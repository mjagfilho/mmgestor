package br.com.mmgestor.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import br.com.mmgestor.web.rest.TestUtil;

public class DadosAssociacaoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DadosAssociacao.class);
        DadosAssociacao dadosAssociacao1 = new DadosAssociacao();
        dadosAssociacao1.setId(1L);
        DadosAssociacao dadosAssociacao2 = new DadosAssociacao();
        dadosAssociacao2.setId(dadosAssociacao1.getId());
        assertThat(dadosAssociacao1).isEqualTo(dadosAssociacao2);
        dadosAssociacao2.setId(2L);
        assertThat(dadosAssociacao1).isNotEqualTo(dadosAssociacao2);
        dadosAssociacao1.setId(null);
        assertThat(dadosAssociacao1).isNotEqualTo(dadosAssociacao2);
    }
}
