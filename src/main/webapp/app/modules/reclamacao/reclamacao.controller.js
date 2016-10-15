(function () { 'use strict;'

    angular
    .module('reclamacaoModule')
    .controller('ReclamacaoController', 
    		['$scope','ReclamacaoService','uiGridConstants','$state','$uibModal','MotivoReclamacaoService','RepresentanteService', 
                 function($scope,ReclamacaoService,uiGridConstants,$state,$uibModal,MotivoReclamacaoService,RepresentanteService) {

    	$scope.aoData = [];
    	
    	MotivoReclamacaoService.buscar().then(
				function(_oReturn) {
					$scope.aMotivoReclamacoes = _oReturn.data;
				});
    	
		RepresentanteService.buscarLight().then(function(_oReturn) {
			$scope.aRepresentantes = _oReturn.data;
		});

        $scope.fnBuscar = function () {
        	if($scope.filtro != null && $scope.filtro.data != undefined) {
        		$scope.filtro.dataReclamacao = $scope.filtro.data.getTime();
        	}
        	
            ReclamacaoService
            .buscar($scope.filtro)
            .then(function(_oReturn) {
                $scope.aoData = _oReturn.data;
            });
        };

        $scope.fnNovo = function () {
            $state.go('app.private.reclamacaoEditar');
        };

        $scope.fnEditar = function (_oObj) {
        	
        	ReclamacaoService.buscarReclamacao(_oObj.id).then(function(_oRetorno) { 
        		_oObj = _oRetorno.data;
 			   $state.go('app.private.reclamacaoEditar', {reclamacao: _oObj});
 		   })
        };
        
		$scope.dateOptions = {
				minDate : new Date(2016,07,01),
				formatYear : 'yy',
				startingDay : 1,
				dateDisabled : false,
				showWeeks: false
		};

		$scope.datePickerPopup = {
			opened : false
		};

		$scope.fnOpen = function() {
			$scope.datePickerPopup.opened = true;
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
 			        		    return ReclamacaoService.excluir(_oObj.id);
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

        $scope.fnBuscar();
        
        $scope.fnImgIndicador= function(indicador) {
        	if (indicador != undefined ) {
        		if (indicador === 1){
        			return "./app/dist/img/bom.png";
        		} else if ( indicador === 2 ){
        			return "./app/dist/img/regular.png";
        		} else if ( indicador === 3 ) {
        			return "./app/dist/img/ruim.png";
        		}
        	}
        	return "";
        }

        $scope.gridReclamacaoOptions = {
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
         		    cellTemplate : '<div class="ui-grid-cell-contents "> <img ng-src="{{grid.appScope.fnImgIndicador(row.entity.indicadorNivelSatisfacao)}}" ng-if="row.entity.indicadorNivelSatisfacao !== undefined" /></div>', 
                 	name : ' ',
                 	width            : 35
                },{
                    field       : 'motivoReclamacao',
                 	displayName : 'Motivo Reclamação'
                },{
                    field       : 'nomeRepresentante',
                 	displayName : 'Representante'
                },{
                    field       : 'statusReclamacao',
                 	displayName : 'Status'
                },{
                    field 		: 'dataReclamacao',
                    cellFilter  : 'date:"dd/MM/yyyy HH:mm"',
                    displayName : 'Data Reclamação'
                },{
                   	name         : 'Reclamação',
         		    cellTemplate : '<div class="ui-grid-cell-contents "> {{row.entity.descricao.substr(0,99);}}</div>' 
               },{
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
