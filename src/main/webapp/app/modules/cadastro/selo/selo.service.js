(function() { 'use strict';

    angular
    .module('seloModule')
    .factory('SeloService', ['$resource',function($resource) {

    	var _rsSelo = $resource('/stampyu-web/rest/selo/:id',{},{
            'update': { method:'PUT' }
        });

        return {
        	buscarSelos : function (_oFiltros) {
        		return _rsSelo.get(_oFiltros).$promise;
        	},
        	getSelo : function (_id) {
        		return _rsSelo.get({id:_id}).$promise;
        	},
        	inserirSelo : function (_oselo) {
    	    	return _rsSelo.save(_oselo).$promise;
    	    },
    	    alterarSelo : function (_oselo) {
    	    	return _rsSelo.update(_oselo).$promise;
    	    },
    	    deletarSelo : function (_id) {
    	    	return _rsSelo.delete({id:_id}).$promise;
    	    }
		};
    }]);

})();
