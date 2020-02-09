package br.com.mmgestor.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import br.com.mmgestor.web.rest.TestUtil;

public class HarasTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Haras.class);
        Haras haras1 = new Haras();
        haras1.setId(1L);
        Haras haras2 = new Haras();
        haras2.setId(haras1.getId());
        assertThat(haras1).isEqualTo(haras2);
        haras2.setId(2L);
        assertThat(haras1).isNotEqualTo(haras2);
        haras1.setId(null);
        assertThat(haras1).isNotEqualTo(haras2);
    }
}
