-- ########## INFORMACAO ##########################
-- By default, Hibernate will search for the import.sql file in the root of the classpath of the produced archive. 
-- For a WAR file, it is located at WEB-INF/classes/import.sql, 
-- but if you generate a regular JAR just place the import.sql in the root of the file.
-- ################################################

INSERT INTO public.t_tipo_usuario (id,descricao,excluido) VALUES (1,'Administrador',false),(2,'Usuário',false);

-- SENHA : '123'
INSERT INTO public.t_usuario(id,ativo,excluido,usuario,nome,login,senha,email,fk_tipo_usuario) VALUES (1, true, false, 'adm', 'ADM','adm','qrsfry34OkbDcI+NjskGTQ==','adm@gmail.com', 1);
INSERT INTO public.t_modulo (id,ordem_exibicao,nome,excluido) VALUES (1,1,'Segurança',false);
INSERT INTO public.t_funcionalidade (id,ordem_exibicao,descricao,fk_modulo,descricao_resumida,excluido,item_menu,url) VALUES (1,1,'Funcionalidade',1,'Funcionalidade',false,true,'#/funcionalidade'),(2,2,'Módulo',1,'Módulo',false,true,'#/modulo'),(3,3,'Perfil',1,'Perfil',false,true,'#/perfil'),(4,4,'Usuário',1,'Usuário',false,true,'#/usuario');
INSERT INTO public.t_funcionalidade_tipo_usuario (funcionalidade_id,tiposusuario_id) VALUES (1,1),(2,1),(3,1),(4,1);
INSERT INTO public.t_perfil (id,excluido,nome,fk_tipo_usuario) VALUES (1,false,'Administrador',1), (2,false,'Usuario',2);
INSERT INTO public.t_perfil_funcionalidade (funcionalidades_id,perfil_id) VALUES (1,1),(2,1),(3,1),(4,1);
INSERT INTO public.t_usuario_perfil (usuario_id, perfis_id) VALUES (1,1);