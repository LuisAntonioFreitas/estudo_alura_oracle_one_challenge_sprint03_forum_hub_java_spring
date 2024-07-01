-- MySQL
CREATE PROCEDURE `tb_usuarios_param_listar`(
	IN _nome            mediumtext,
	IN _email           mediumtext,
	IN _perfil          mediumtext
)
BEGIN
    DECLARE _sp_nome    mediumtext;
    DECLARE _sp_email   mediumtext;
    DECLARE _sp_perfil  mediumtext;

    SET _sp_nome        = IFNULL(_nome, NULL);
    SET _sp_email       = IFNULL(_email, NULL);
    SET _sp_perfil      = IFNULL(_perfil, NULL);

    START TRANSACTION;

	SELECT 	u.id, u.nome, u.login, u.email,
			p.nome AS perfil,
			u.ativo, u.created_at, u.updated_at
    FROM    tb_usuarios AS u
    INNER JOIN  tb_perfis AS p ON (u.id = p.id)
    WHERE   ( IFNULL(u.nome,'') LIKE CONCAT('%', _sp_nome, '%') OR _sp_nome IS NULL )
    AND     ( IFNULL(u.email, '') LIKE CONCAT('%', _sp_email, '%') OR _sp_email IS NULL )
    AND     ( IFNULL(p.nome, '') LIKE CONCAT('%', _sp_perfil, '%') OR _sp_perfil IS NULL )
    ORDER BY u.nome ASC;

    COMMIT;
END;