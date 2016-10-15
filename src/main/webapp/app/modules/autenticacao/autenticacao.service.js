(function() { 'use strict';

    angular
    .module('autenticacaoModule')
    .factory('AutenticacaoService', ['$resource','AppService', function($resource,AppService) {

    	var _sUrlBase = AppService.getUrl();

        var _rs = $resource(_sUrlBase + 'rest/usuario/:action', {}, {
        	tipoUsuario : {
            	method  : 'GET',
    			params  : {action: 'tipoUsuario'}
            },
            buscarUsuarioAdmin : {
            	method  : 'GET',
    			params  : {action: 'admin'}
            }
        });

    	var _rsModulo = $resource(_sUrlBase + 'rest/modulo/:moduloId',{},{
            'update': { method:'PUT' }
        });
    	var _rsPerfil = $resource(_sUrlBase + 'rest/perfil/:perfilId',{},{
            'update': { method:'PUT' }
        });
    	var _rsFuncionalidade = $resource(_sUrlBase + 'rest/funcionalidade/:funcionalidadeId',{},{
            'update': { method:'PUT' }
        });
    	var _rsUsuario = $resource(_sUrlBase + 'rest/usuario/:userId',{},{
            'update': { method:'PUT' }
        });
    	
        return {
        	tipoUsuario : function (_oUsuario) {
        		return _rs.tipoUsuario().$promise;
        	},
        	buscarModulo : function(_id) {
        		return _rsModulo.get(_id).$promise;
        	},
    	    inserirModulo : function(_modulo) {
    	    	return _rsModulo.save(_modulo).$promise;
    	    },
    	    alterarModulo : function(_modulo) {
    	    	return _rsModulo.update(_modulo).$promise;
    	    },
    	    deletarModulo : function(_id) {
    	    	return _rsModulo.delete({moduloId:_id}).$promise;
    	    },
        	buscarPerfil : function(_id) {
        		return _rsPerfil.get(_id).$promise;
        	},
    	    inserirPerfil : function(_perfil) {
    	    	return _rsPerfil.save(_perfil).$promise;
    	    },
    	    alterarPerfil : function(_perfil) {
    	    	return _rsPerfil.update(_perfil).$promise;
    	    },
    	    deletarPerfil : function(_id) {
    	    	return _rsPerfil.delete({perfilId:_id}).$promise;
    	    },
        	buscarFuncionalidade : function(_id) {
        		return _rsFuncionalidade.get(_id).$promise;
        	},
    	    inserirFuncionalidade : function(_funcionalidade) {
    	    	return _rsFuncionalidade.save(_funcionalidade).$promise;
    	    },
    	    alterarFuncionalidade : function(_funcionalidade) {
    	    	return _rsFuncionalidade.update(_funcionalidade).$promise;
    	    },
    	    deletarFuncionalidade : function(_id) {
    	    	return _rsFuncionalidade.delete({funcionalidadeId:_id}).$promise;
    	    },
    	    buscarUsuarioAdmin : function(_oFiltro) {
        		return _rs.get(_oFiltro).$promise;
        	},
        	buscarUsuario : function(_oFiltro) {
        		return _rsUsuario.get(_oFiltro).$promise;
        	},
    	    inserirUsuario : function(_usuario) {
    	    	return _rsUsuario.save(_usuario).$promise;
    	    },
    	    alterarUsuario : function(_usuario) {
    	    	return _rsUsuario.update(_usuario).$promise;
    	    },
    	    deletarUsuario : function(_id) {
    	    	return _rsUsuario.delete({userId:_id}).$promise;
    	    }
		};
    }]);
})();