(function () { 'use strict;';

	angular
	.module('representanteModule')
	.controller('RepresentanteEditarController', [
       '$scope','$rootScope','RepresentanteService','$state','$stateParams','ProdutoService','FabricanteService','MedicoService','LojaService', '$timeout','$uibModal',
       function($scope,$rootScope,RepresentanteService,$state,$stateParams,ProdutoService,FabricanteService, MedicoService, LojaService,$timeout,$uibModal) {

    	   $scope.representante = {
               produtos : [],
               medicos  : [],
               lojas 	: []
    	   };

    	   $scope.oProdutoSelecionado = undefined;
    	   $scope.aProdutos = [];
    	   $scope.oMedicoSelecionado = undefined;
    	   $scope.aMedicos = [];
    	   $scope.oLojaSelecionado = undefined;
    	   $scope.aLojas = [];
    	   
    	   $scope.fnPad=function(num,field){
    		    var n = '' + num;	
    		    var w = n.length;
    		    var l = field.length;
    		    var pad = w < l ? l-w : 0;
    		    return field.substr(0,pad) + n;
    		};
    		
    		
    		
            $scope.fnPesquisaMedicos = function (sCriterioBusca) {

            	if(sCriterioBusca  && sCriterioBusca.length > 3){
    	        	return MedicoService
    	            .buscar({ search : sCriterioBusca })
    	            .then(function(_oReturn) {
    	            	return _oReturn.data;
    	            });
            	}
            };
            

            $scope.fnPesquisaProdutos = function (sCriterioBusca) {

            	if(sCriterioBusca  && sCriterioBusca.length > 2){
    	        	return ProdutoService
    	            .buscar({ search : sCriterioBusca , fabricanteID : $scope.representante.fabricante.id })
    	            .then(function(_oReturn) {
    	            	return _oReturn.data;
    	            });
            	}
            };
            
            $scope.fnPesquisaLojas = function (sCriterioBusca) {

            	if(sCriterioBusca  && sCriterioBusca.length > 2){
    	        	return LojaService
    	            .buscar({ nome : sCriterioBusca })
    	            .then(function(_oReturn) {
    	            	return _oReturn.data;
    	            });
            	}
            };
            
            $scope.fnPesquisaFabricantes = function (sCriterioBusca) {

            	if(sCriterioBusca  && sCriterioBusca.length > 1){
    	        	return FabricanteService
    	            .buscar({ nome : sCriterioBusca})
    	            .then(function(_oReturn) {
    	            	return _oReturn.data;
    	            });
            	}
            };
            

    	   var filtro = {};
		   filtro.soComProdutos =  true;
		   
    	  
    	   if ($stateParams.representante) {
    		   $scope.representante = $stateParams.representante;
    		   $scope.representante.usuario.confirmarSenha = $scope.representante.usuario.senha; 
    	   }

    	   var _fnIrParaListagem = function () {
    		   $state.go('app.private.representante');
    	   };

    	   $scope.fnAddProduto = function () {
    		   if ($scope.oProdutoSelecionado && $scope.oProdutoSelecionado.id && _validateItem($scope.representante.produtos, $scope.oProdutoSelecionado)) {
    			   $scope.representante.produtos.push($scope.oProdutoSelecionado);
    			   $scope.oProdutoSelecionado = undefined;
    		   }
    	   };
    	   
    	   $scope.fnAddMedico = function () {
    		   if ($scope.oMedicoSelecionado && _validateItem($scope.representante.medicos, $scope.oMedicoSelecionado)) {
    			   $scope.representante.medicos.push($scope.oMedicoSelecionado);
    			   $scope.oMedicoSelecionado = undefined;
    		   }
    		   
    		   
    		   
    	   };
    	   
    	   $scope.fnAddLoja = function () {
    		   if ($scope.oLojaSelecionado && _validateItem($scope.representante.lojas, $scope.oLojaSelecionado)) {
    			   $scope.representante.lojas.push($scope.oLojaSelecionado);
    			   $scope.oLojaSelecionado = undefined;
    		   }
    	   };

    	   $scope.fnExcluirProduto = function (_oObj) {
    		   if (_oObj) {
    			   var index = $scope.representante.produtos.indexOf(_oObj);
    			   $scope.representante.produtos.splice(index, 1);
    		   }
    	   };
    	   
    	   $scope.fnExcluirLoja = function (_oObj) {
    		   if (_oObj) {
    			   var index = $scope.representante.lojas.indexOf(_oObj);
    			   $scope.representante.lojas.splice(index, 1);
    		   }
    	   };
    	   
    	   $scope.fnExcluirMedico = function (_oObj) {
    		   if (_oObj) {
    			   var index = $scope.representante.medicos.indexOf(_oObj);
    			   $scope.representante.medicos.splice(index, 1);
    		   }
    	   };

    	   $scope.fnSalvar = function () {

    		   if($scope.representante.id != null) {
    			   RepresentanteService
        		   .alterar($scope.representante)
        		   .then(function(_oReturn) {
        			   //_fnIrParaListagem();
        		   });
    		   } else {
    			   RepresentanteService
        		   .inserir($scope.representante)
        		   .then(function(_oReturn) {
        			   //_fnIrParaListagem();
        		   });
    		   }
    	   };

           $scope.dateOptions = {
               minDate       : new Date(),
               showWeeks     : false,
               formatYear    : 'yy',
               startingDay   : 1,
               dateDisabled  : false
           };

           $scope.datePickerPopup = {
               opened : false
           };

           $scope.fnOpen = function() {
               $scope.datePickerPopup.opened = true;
           };

           $scope.$on('$typeahead.select', function(event, value, index, elem){
        	   console.log("Value/elem " + value,elem)
        	   
        	   $scope.user.full_name = value;
        	     
        	});
           
           $scope.gridProdutoOptions = {
               data       : 'representante.produtos',
               columnDefs : [{
                   field       : 'descricao',
                   displayName : 'Descrição'
                 },{
                     field       : 'ean',
                     displayName : 'EAN',
                     width       : 200,
                 },
                 {
                   field       : 'preco',
                   displayName : 'Preço',
                   cellFilter: 'currency',
                   width       : 110,
                 }
                 ,{
                   name             : '..',
                   width            : 35,
                   cellClass        : 'text-center',
                   cellTemplate     : '<a href="" ng-click="grid.appScope.fnExcluirProduto(row.entity)"><span class="glyphicon glyphicon-remove"></span></a>',
                   enableSorting    : false,
                   enableColumnMenu : false
               }]
            };
           
           $scope.gridLojasOptions = {
                   data       : 'representante.lojas',
                   columnDefs : [{
                       field       : 'nome',
                       displayName : 'Nome'
                     },{
                       field       : 'telefone',
                       displayName : 'Telefone',
                       width       : 90,
                       
                     },{
                         field       : 'logradouro',
                         displayName : 'Endereço'
                         
                     },{
                         field       : 'bairro',
                         displayName : 'Bairro'
                         
                     },{
                       name             : '..',
                       width            : 35,
                       cellClass        : 'text-center',
                       cellTemplate     : '<a href="" ng-click="grid.appScope.fnExcluirLoja(row.entity)"><span class="glyphicon glyphicon-remove"></span></a>',
                       enableSorting    : false,
                       enableColumnMenu : false
                   }]
                };
           
           $scope.gridMedicoOptions = {
                   data       : 'representante.medicos',
                   columnDefs : [{
                       field       : 'nome',
                       displayName : 'Nome'
                     },{
                       field       : 'crm',
                       displayName : 'CRM',
                       width       : 150,
                     },{
                       name             : '..',
                       width            : 35,
                       cellClass        : 'text-center',
                       cellTemplate     : '<a href="" ng-click="grid.appScope.fnExcluirMedico(row.entity)"><span class="glyphicon glyphicon-remove"></span></a>',
                       enableSorting    : false,
                       enableColumnMenu : false
                   }]
            };
           
    	   var _validateItem = function(list, object) {
	   		    var canAdd = true;
	   		    angular.forEach(list, function(value, key) {
	   		    	if(value.id === object.id) {
	   		    		canAdd = false;
	   		    		return;
	   		    	}
	  		    	});
	   		    return canAdd;
	   		    
	   	   };
	   	   
		   	$scope.fnTrocarSenha = function () {
	       		
		   		$scope.representante.usuario.senha = "";
		   		$scope.representante.usuario.confirmarSenha = "";
		   		$uibModal
	            .open({
	                controller  : 'ModalTrocarSenhaUsuarioController',
	                templateUrl : 'modal-trocar-senha-template.html',
	                resolve     : {
	             	   usuario:$scope.representante.usuario                     
	                }
	            }) 
	            .result.then(function (_oModalRetorno) {}, function () {
	         	   
	            });
	 	   };

       }])
       .controller('ModalTrocarSenhaUsuarioController', 
               ['$scope','$uibModalInstance','usuario','RepresentanteService','$q',
               function ($scope,$uibModalInstance,usuario,RepresentanteService,$q ) {

            	   $scope.representante = { usuario : usuario} ;
            	   console.info(usuario);
            	   
            	   	$scope.fnModalOk = function () {

                	   console.log($scope.representante.usuario.senha);
                	   console.log($scope.representante.usuario.confirmarSenha);
                	   
                	   if($scope.representante.usuario.senha != undefined && $scope.representante.usuario.confirmarSenha != undefined){
                		   
                		   RepresentanteService.trocarSenha($scope.representante.usuario)
                		   .then(function (_oResponse) 
                				   { 
                			   		console.info(_oResponse);
                			   		$uibModalInstance.close();
                				   }, function (_oErr) {
                					   console.error(_oErr);
                				   });
                	   }
                   };

                   $scope.fnModalCancel = function () {
                       $uibModalInstance.dismiss('cancel');
                   };
        }]);;
})()