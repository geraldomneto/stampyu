(function () { 'use strict;'

    angular
    .module('homeModule')
    .controller('HomeController', ['$scope', 'Menu','ReclamacaoService', 'RepresentanteService','ProdutoService', 'ProdutoVendaService', function($scope, Menu, ReclamacaoService, RepresentanteService, ProdutoService, ProdutoVendaService) {

        $scope.dataHoje = new Date();
        $scope.menu = Menu.data;
        $scope.numeroReclamacoes = 0;
        $scope.numeroRepresentantes = 0;
        $scope.numeroProdutosControlados = 0;
        $scope.dataSourceGrafico;

        $scope.filtro = {statusID:0};
        
        ReclamacaoService
        .count()
        .then(function(_oReturn) {
        	$scope.numeroReclamacoes = _oReturn.data;
        });
        
        RepresentanteService
        .count()
        .then(function(_oReturn) {
        	$scope.numeroRepresentantes = _oReturn.data;
        });
        
        ProdutoService
        .count()
        .then(function(_oReturn) {
        	$scope.numeroProdutosControlados = _oReturn.data;
        });
        
        
        
        ProdutoVendaService
        .buscarDataSourceGraficoTopProdutosVendas()
        .then(function(_oReturn) {
        	console.log(_oReturn.data);
        	$scope.dataSourceTopGrafico = _oReturn.data;
        	
        	// 'hishart-id'
            Highcharts.chart('chart-top-produto', {
            	
            	
            	chart: {
                    type: 'column'
                },
            	title: {
                    text: 'Top 8 produtos mais vendidos com receita'
                },
                xAxis: {
                    categories: $scope.dataSourceTopGrafico.listData
                },
                series: [{
                    data : $scope.dataSourceTopGrafico.listQuantidade,
                    name : 'Quantidade',
                    colorByPoint: true
                }]
            });
            
            
        });
        
        ProdutoVendaService
        .buscarDataSourceGraficoTopMedicos()
        .then(function(_oReturn) {
        	console.log(_oReturn.data);
        	$scope.dataSourceTopGraficoMedico = _oReturn.data;
        	
        	// 'hishart-id'
            Highcharts.chart('chart-top-medico', {
            	
            	
            	chart: {
                    type: 'column'
                },
            	title: {
                    text: 'Top 8 médicos'
                },
                xAxis: {
                    categories: $scope.dataSourceTopGraficoMedico.listData
                },
                series: [{
                    data : $scope.dataSourceTopGraficoMedico.listQuantidade,
                    name : 'Quantidade',
                    colorByPoint: true
                }]
            });
            
            
        });
        
        ProdutoVendaService
        .buscarDataSourceGraficoTopRepresentantes()
        .then(function(_oReturn) {
        	console.log(_oReturn.data);
        	$scope.dataSourceTopGraficoRepresentante = _oReturn.data;
        	
        	// 'hishart-id'
            Highcharts.chart('chart-top-representante', {
            	
            	
            	chart: {
                    type: 'column'
                },
            	title: {
                    text: 'Top 10 representantes'
                },
                xAxis: {
                    categories: $scope.dataSourceTopGraficoRepresentante.listData
                },
                series: [{
                    data : $scope.dataSourceTopGraficoRepresentante.listQuantidade,
                    name : 'Vendas',
                    colorByPoint: true
                }]
            });
            
            
        });
        
//        
//        ProdutoVendaService
//        .buscarDataSourceGraficoVendas()
//        .then(function(_oReturn) {
//        	console.log(_oReturn.data);
//        	$scope.dataSourceGrafico = _oReturn.data;
//        	
//        	// 'hishart-id'
//            Highcharts.chart('hishart-id', {
//                title: {
//                    text: 'Vendas/mês'
//                },
//                xAxis: {
//                    categories: $scope.dataSourceGrafico.listData
//                },
//                series: [{
//                    data : $scope.dataSourceGrafico.listQuantidade,
//                    name : 'Vendas'
//                }]
//            });
//            
//        });
        
        
        
    }]);
})()
