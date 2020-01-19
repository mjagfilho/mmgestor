import { element, by, ElementFinder } from 'protractor';

export class DadosAssociacaoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-dados-associacao div table .btn-danger'));
  title = element.all(by.css('jhi-dados-associacao div h2#page-heading span')).first();

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class DadosAssociacaoUpdatePage {
  pageTitle = element(by.id('jhi-dados-associacao-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  criadorInput = element(by.id('field_criador'));
  proprietarioInput = element(by.id('field_proprietario'));
  livroInput = element(by.id('field_livro'));
  registroInput = element(by.id('field_registro'));
  exameDNAInput = element(by.id('field_exameDNA'));
  chipInput = element(by.id('field_chip'));
  ehBloqueadoInput = element(by.id('field_ehBloqueado'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCriadorInput(criador: string): Promise<void> {
    await this.criadorInput.sendKeys(criador);
  }

  async getCriadorInput(): Promise<string> {
    return await this.criadorInput.getAttribute('value');
  }

  async setProprietarioInput(proprietario: string): Promise<void> {
    await this.proprietarioInput.sendKeys(proprietario);
  }

  async getProprietarioInput(): Promise<string> {
    return await this.proprietarioInput.getAttribute('value');
  }

  async setLivroInput(livro: string): Promise<void> {
    await this.livroInput.sendKeys(livro);
  }

  async getLivroInput(): Promise<string> {
    return await this.livroInput.getAttribute('value');
  }

  async setRegistroInput(registro: string): Promise<void> {
    await this.registroInput.sendKeys(registro);
  }

  async getRegistroInput(): Promise<string> {
    return await this.registroInput.getAttribute('value');
  }

  async setExameDNAInput(exameDNA: string): Promise<void> {
    await this.exameDNAInput.sendKeys(exameDNA);
  }

  async getExameDNAInput(): Promise<string> {
    return await this.exameDNAInput.getAttribute('value');
  }

  async setChipInput(chip: string): Promise<void> {
    await this.chipInput.sendKeys(chip);
  }

  async getChipInput(): Promise<string> {
    return await this.chipInput.getAttribute('value');
  }

  getEhBloqueadoInput(): ElementFinder {
    return this.ehBloqueadoInput;
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class DadosAssociacaoDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-dadosAssociacao-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-dadosAssociacao'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
