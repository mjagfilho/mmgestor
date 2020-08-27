package br.com.mmgestor.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.mmgestor.web.rest.TestUtil;

public class AssociadosHarasTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssociadosHaras.class);
        AssociadosHaras associadosHaras1 = new AssociadosHaras();
        associadosHaras1.setId(1L);
        AssociadosHaras associadosHaras2 = new AssociadosHaras();
        associadosHaras2.setId(associadosHaras1.getId());
        assertThat(associadosHaras1).isEqualTo(associadosHaras2);
        associadosHaras2.setId(2L);
        assertThat(associadosHaras1).isNotEqualTo(associadosHaras2);
        associadosHaras1.setId(null);
        assertThat(associadosHaras1).isNotEqualTo(associadosHaras2);
    }
}
