/**
 * 
 */
(function () { 'use strict';

    angular
    .module('appMain')
    .controller('ModalExluirCtrl', 
           ['$scope','$uibModalInstance','oObjeto',
           function ($scope, $uibModalInstance,oObjeto) {

    	   $scope.oModalRetorno = {
    		  bExcluido : false
           };

          $scope.fnModalOk = function () {
        	  $uibModalInstance.close(oObjeto.fnCallback());
		  };

		  $scope.fnModalCancel = function () {
		    $uibModalInstance.dismiss('cancel');
		  };
    }]);
})();
