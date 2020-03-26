package br.com.mmgestor.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.mmgestor.web.rest.TestUtil;

public class TipoAssociadoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoAssociado.class);
        TipoAssociado tipoAssociado1 = new TipoAssociado();
        tipoAssociado1.setId(1L);
        TipoAssociado tipoAssociado2 = new TipoAssociado();
        tipoAssociado2.setId(tipoAssociado1.getId());
        assertThat(tipoAssociado1).isEqualTo(tipoAssociado2);
        tipoAssociado2.setId(2L);
        assertThat(tipoAssociado1).isNotEqualTo(tipoAssociado2);
        tipoAssociado1.setId(null);
        assertThat(tipoAssociado1).isNotEqualTo(tipoAssociado2);
    }
}
