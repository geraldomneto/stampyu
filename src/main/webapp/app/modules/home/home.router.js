(function () { 'use strict;'

	angular
	.module('homeModule')
	.config(['$stateProvider' ,  
	         function($stateProvider) {

		$stateProvider
		.state('app.private.home', {
            url         : '/home',
            controller  : 'HomeController',
            templateUrl : './app/modules/home/home.view.html'
        });

	}]);
})()