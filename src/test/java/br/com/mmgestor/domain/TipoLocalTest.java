package br.com.mmgestor.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import br.com.mmgestor.web.rest.TestUtil;

public class TipoLocalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoLocal.class);
        TipoLocal tipoLocal1 = new TipoLocal();
        tipoLocal1.setId(1L);
        TipoLocal tipoLocal2 = new TipoLocal();
        tipoLocal2.setId(tipoLocal1.getId());
        assertThat(tipoLocal1).isEqualTo(tipoLocal2);
        tipoLocal2.setId(2L);
        assertThat(tipoLocal1).isNotEqualTo(tipoLocal2);
        tipoLocal1.setId(null);
        assertThat(tipoLocal1).isNotEqualTo(tipoLocal2);
    }
}
