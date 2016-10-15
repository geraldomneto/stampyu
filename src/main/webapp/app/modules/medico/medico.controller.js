(function () { 'use strict';

    angular
    .module('medicoModule')
    .controller('MedicoController', ['$scope','MedicoService','uiGridConstants','$state','$uibModal', 'growl',
                                     function($scope,MedicoService,uiGridConstants,$state,$uibModal,growl) {

    	$scope.aoData = [];
    	$scope.filtro = {nome: "" , crm : "" , codigo : ""};
    	
        $scope.fnBuscar = function () {

        	if($scope.filtro.nome == "" && $scope.filtro.crm == "" && $scope.filtro.codigo == "" ){
        		growl.warning("É necessário selecionar algum filtro para fazer a pesquisa.",{title: 'Atenção!'});
        		return;
        	}
        	
            MedicoService
            .buscar($scope.filtro)
            .then(function(_oReturn) {
                $scope.aoData = _oReturn.data;
            });
        };

        $scope.fnNovo = function () {
            $state.go('app.private.medicoEditar');
        };

        $scope.fnEditar = function (_oObj) {
            $state.go('app.private.medicoEditar', {medico: _oObj});
        };

        $scope.fnExcluir = function (_oObj) {

            $uibModal
            .open({
                controller  : 'ModalExluirCtrl',
                templateUrl : 'modal-exluir-template.html',
                resolve     : {
 			        oObjeto: function () {
 			          return {
 			        	    fnCallback : function () {
 			        		    return MedicoService.excluir(_oObj.id);
 			        	    }
 			            };
 			        }
			    }
            })
            .result.then(function (_oModalRetorno) {

                _oModalRetorno.$promise.then(
                    function (_oReturn) {
                        var index = $scope.aoData.indexOf(_oObj);
                        $scope.aoData.splice(index, 1);
                    }, 
                    function (_oErroRetorno) { 
                        console.error("Erro ao excluir!"); 
                    }
                );

			}, function () {
			    console.info('Adeus modal: ' + new Date());
			});
 	   };

       

        $scope.gridMedicoOptions = {
            data                 : 'aoData',
            enableSorting        : true,
            paginationPageSizes  : [10],
            enableColumnResizing : true,
            columnDefs           : [
                {
                    name             : '.',
                    width            : 35,
                    cellClass        : 'text-center',
                    cellTemplate     : '<a href="" ng-click="grid.appScope.fnEditar(row.entity)"><span class="glyphicon glyphicon-edit"></span></a>',
                    enableSorting    : false,
                    enableColumnMenu : false
 		        },{
                    field       : 'nome',
                 	displayName : 'Nome',
                 	sort        : {
                 	    priority  : 0,
                 	    direction : uiGridConstants.ASC
                    }
                },{
                	field       : 'crm',
                 	displayName : 'CRM'
                },{
                	field       : 'siglaUF',
                 	displayName : 'UF'
                },{
                	field       : 'codigo',
                 	displayName : 'Codigo'
                }
                ,{
                    name             : '..',
                    width            : 35,
                    cellClass        : 'text-center',
                    cellTemplate     : '<a href="" ng-click="grid.appScope.fnExcluir(row.entity)"><span class="glyphicon glyphicon-remove"></span></a>',
                    enableSorting    : false,
                    enableColumnMenu : false
            }],
            onRegisterApi : function (gridApi) {
                $scope.gridApi = gridApi;
            }
        };

    }]);
})()
