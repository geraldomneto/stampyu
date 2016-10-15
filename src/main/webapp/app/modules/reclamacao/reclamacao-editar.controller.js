(function() {
	'use strict;'

	angular.module('reclamacaoModule').controller('ReclamacaoEditarController',[
					'$scope','$rootScope','ReclamacaoService','MotivoReclamacaoService',
					'MotivoInsatisfacaoService','AutenticacaoService','RepresentanteService',
					'$state','$stateParams','ProdutoService','LojaService',
					function($scope, $rootScope, ReclamacaoService,
							MotivoReclamacaoService, MotivoInsatisfacaoService,
							AutenticacaoService, RepresentanteService, $state,
							$stateParams,ProdutoService, LojaService) {
						
						$scope.disabledDataReclamacao = false;
						$scope.disabledDataResposta = false;
						
						
			    	   ProdutoService
			    	   .buscar()
			    	   .then(function(_oReturn) {
			   	           $scope.aProdutos = _oReturn.data;
			   		   });
			    	   
			    	   LojaService
			    	   .buscar()
			    	   .then(function(_oReturn) {
			   	           $scope.aLojas = _oReturn.data;
			   		   });

						MotivoReclamacaoService.buscar().then(
								function(_oReturn) {
									$scope.aMotivoReclamacoes = _oReturn.data;
								});

						MotivoInsatisfacaoService.buscar().then(
								function(_oReturn) {
									$scope.aMotivoInsatisfacoes = _oReturn.data;
								});

						$scope.aUsuarios = [];
						$scope.aUsuarios.push($rootScope.usuarioLogado);

						RepresentanteService.buscarLight().then(function(_oReturn) {
							$scope.aRepresentantes = _oReturn.data;
						});

						if ($stateParams.reclamacao) {
							$scope.reclamacao = $stateParams.reclamacao;
							if($scope.reclamacao.dataReclamacao != undefined) {
								$scope.disabledDataReclamacao = true;
							}
							if($scope.reclamacao.dataResposta != undefined) {
								$scope.disabledDataResposta = true;
							}
							$scope.reclamacao.responsavelResposta = $rootScope.usuarioLogado;
							$scope.reclamacao.dataResposta = new Date();
						}

						var _fnIrParaListagem = function() {
							$state.go('app.private.reclamacao');
						};

						$scope.fnSalvar = function() {

							if($scope.reclamacao.representante != undefined){
								$scope.reclamacao.representante.listIdProduto = undefined;
							}

							if ($scope.reclamacao.id != null) {
								ReclamacaoService.alterar($scope.reclamacao)
									.then(function(_oReturn) {
										_fnIrParaListagem();
									});
							} else {
								ReclamacaoService.inserir($scope.reclamacao)
									.then(function(_oReturn) {
										_fnIrParaListagem();
									});
							}
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
						$scope.datePickerPopup2 = {
								opened : false
							};

						$scope.fnOpen2 = function() {
							$scope.datePickerPopup2.opened = true;
						};
						
					} ]);
})()