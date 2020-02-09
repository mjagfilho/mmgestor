package br.com.mmgestor.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.mmgestor.web.rest.TestUtil;

public class AssociadoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Associado.class);
        Associado associado1 = new Associado();
        associado1.setId(1L);
        Associado associado2 = new Associado();
        associado2.setId(associado1.getId());
        assertThat(associado1).isEqualTo(associado2);
        associado2.setId(2L);
        assertThat(associado1).isNotEqualTo(associado2);
        associado1.setId(null);
        assertThat(associado1).isNotEqualTo(associado2);
    }
}
