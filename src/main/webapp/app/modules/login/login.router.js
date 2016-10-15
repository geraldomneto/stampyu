(function () { 'use strict;'

	angular
    .module('loginModule')
	.config(['$stateProvider' ,  
	         function($stateProvider) {

		$stateProvider
		.state('app.public.login', {
            url         : '/login',
            templateUrl : './app/modules/login/login.view.html',
            controller  : 'LoginController'
        }).state('app.public.senha', {
            url         : '/senha',
            templateUrl : './app/modules/login/senha.view.html',
            controller  : 'LoginController'
        }).state('app.public.nova-senha', {
            url         : '/nova-senha',
            templateUrl : './app/modules/login/nova-senha.view.html',
            controller  : 'LoginController'
        });

	}]);
})()