package br.com.mmgestor.service.impl;

import br.com.mmgestor.service.UsuarioService;
import br.com.mmgestor.domain.Usuario;
import br.com.mmgestor.repository.UsuarioRepository;
import br.com.mmgestor.repository.search.UsuarioSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Usuario}.
 */
@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final Logger log = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    private final UsuarioRepository usuarioRepository;

    private final UsuarioSearchRepository usuarioSearchRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, UsuarioSearchRepository usuarioSearchRepository) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioSearchRepository = usuarioSearchRepository;
    }

    /**
     * Save a usuario.
     *
     * @param usuario the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Usuario save(Usuario usuario) {
        log.debug("Request to save Usuario : {}", usuario);
        Usuario result = usuarioRepository.save(usuario);
        usuarioSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the usuarios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Usuario> findAll(Pageable pageable) {
        log.debug("Request to get all Usuarios");
        return usuarioRepository.findAll(pageable);
    }


    /**
     * Get one usuario by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findOne(Long id) {
        log.debug("Request to get Usuario : {}", id);
        return usuarioRepository.findById(id);
    }

    /**
     * Delete the usuario by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Usuario : {}", id);
        usuarioRepository.deleteById(id);
        usuarioSearchRepository.deleteById(id);
    }

    /**
     * Search for the usuario corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Usuario> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Usuarios for query {}", query);
        return usuarioSearchRepository.search(queryStringQuery(query), pageable);    }
}
