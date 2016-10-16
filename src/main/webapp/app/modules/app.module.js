(function () { 'use strict';

    angular
    .module('appMain', [
         'ngResource',
         'ngCookies',
         'ngAnimate',
         'ngTouch',
         'ngSanitize',
         'ui.router',
         'angular-growl',
         // github.com/angular-ui/ui-grid/wiki/Configuration-Options
         'ui.grid',
         'ui.grid.pagination',
         'ui.grid.resizeColumns',
         'ui.grid.edit',
         'ui.grid.grouping',
         'blockUI',
         'ui.bootstrap',
         'angular-media-preview',
         'base64',
         "angucomplete-alt",

         // Modulos BASE
         'loginModule',
         'autenticacaoModule',
         'homeModule',

         // Modulos do negocio
         'fabricanteModule',
         'lojaModule',
         'mensagemModule',
         'motivoInsatisfacaoModule',
         'motivoReclamacaoModule',
         'produtoModule',
         'medicoModule',
         'produtoVendaModule',
         'produtoLojaModule',
         'reclamacaoModule',
         'representanteModule',
         
         //NOVOS STAMPYU
         'seloModule'
    ])
    .run(['$rootScope','LoginService','$state','$cookies','$window', 
         function($rootScope,LoginService,$state,$cookies,$window) {

       $rootScope.local = new LocalStorageEngine();
       $rootScope.previousState;
       $rootScope.showModal = false;

       $rootScope.usuarioLogado = $cookies.getObject('usuarioLogado');

       $rootScope.voltar = function() {

    	   if (!$rootScope.previousState) {
    		   $rootScope.previousState = 'app.private.home';  
    	   }

    	   $state.go($rootScope.previousState);  
       };

	   $rootScope.fnExcluir = function() {
		   return $rootScope.fnDelete();
	   };

       $rootScope.$on('$stateChangeStart', function (event,toState,toStateParams,fromState,fromStateParams) {
    	   	
    	   $rootScope.previousState = fromState.name;
    	    if (toState.name && toState.name.match(/^app\.private\./)) {
    	    	$window.scrollTo(0,0);
    	    	LoginService
    	    	.isAutenticado()
    	    	.then(function (_oResponse) {}, function (_oResponseErro) {
    	    		console.warn('Redirecionando para a tela de login.', _oResponseErro);
    	    		event.preventDefault();
    	    		$state.go('app.public.login');
    	    	});
    	    }
    	  });
    }])
    .config(['$httpProvider','$urlRouterProvider','$stateProvider','blockUIConfig',
         function($httpProvider,$urlRouterProvider,$stateProvider,blockUIConfig) {

            blockUIConfig.message = 'Carregando...';

            $urlRouterProvider.otherwise('/home');

            $stateProvider
            .state('app', {
            	template : '<div ui-view="" data-autoscroll="false"></div>'
              })
            .state('app.public', {
            	templateUrl : './app/modules/_base/public.view.html'
            })
            .state('app.private', {
            	templateUrl : './app/modules/_base/private.view.html',
            	resolve     : {
            		homeService : 'HomeService',
            		Menu : function (homeService) {
            			return homeService.getMenu();
            		}
            	},
            	controller :['$scope', 'Menu', function ($scope, Menu) {
            		$scope.menu = Menu.data;
            	}]
            });

            $httpProvider.interceptors.push(['$q','$injector','growl', function ($q,$injector,growl) {

                return {
                    'response': function(response) {

                	    if( (response.config.method == "POST" || response.config.method == "PUT" || response.config.method == "DELETE") 
                	    		&& response.config.url.indexOf('rest') > 0) {
            	    		growl.success(response.data.message || "Sucesso!",{title: 'Sucesso!'});
                        }

                        return response;
                    },
                    'responseError': function(rejection) {

	                    if(rejection.status === 500 ) {
	                        growl.error("Houve um erro no servidor, por favor tente mas tarde.",{title: 'Error!'});
	                    }
	
	                    if(rejection.status === 405) {
	                        growl.error(rejection.statusText,{title: 'Error!'});
	                    }
		
                        if(rejection.status === 400) { 
                            if (rejection.data && rejection.data.message) {
                                growl.warning(rejection.data.message,{title: 'Atenção!'});
                            } else {
                            	growl.warning('Por favor verifique os valores digitados.',{title: 'Atenção!'});
                            }
                        }
		
		                return $q.reject(rejection);
	                },
	                'request': function(config) {
	                    return config;
	                }
                };
            }]);
    }]);

})();
