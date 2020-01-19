import { element, by, ElementFinder } from 'protractor';

export class ResponsavelComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-responsavel div table .btn-danger'));
  title = element.all(by.css('jhi-responsavel div h2#page-heading span')).first();

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

export class ResponsavelUpdatePage {
  pageTitle = element(by.id('jhi-responsavel-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  cpfInput = element(by.id('field_cpf'));
  nomeCompletoInput = element(by.id('field_nomeCompleto'));
  dtNascimentoInput = element(by.id('field_dtNascimento'));
  enderecoSelect = element(by.id('field_endereco'));
  usuarioSelect = element(by.id('field_usuario'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCpfInput(cpf: string): Promise<void> {
    await this.cpfInput.sendKeys(cpf);
  }

  async getCpfInput(): Promise<string> {
    return await this.cpfInput.getAttribute('value');
  }

  async setNomeCompletoInput(nomeCompleto: string): Promise<void> {
    await this.nomeCompletoInput.sendKeys(nomeCompleto);
  }

  async getNomeCompletoInput(): Promise<string> {
    return await this.nomeCompletoInput.getAttribute('value');
  }

  async setDtNascimentoInput(dtNascimento: string): Promise<void> {
    await this.dtNascimentoInput.sendKeys(dtNascimento);
  }

  async getDtNascimentoInput(): Promise<string> {
    return await this.dtNascimentoInput.getAttribute('value');
  }

  async enderecoSelectLastOption(): Promise<void> {
    await this.enderecoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async enderecoSelectOption(option: string): Promise<void> {
    await this.enderecoSelect.sendKeys(option);
  }

  getEnderecoSelect(): ElementFinder {
    return this.enderecoSelect;
  }

  async getEnderecoSelectedOption(): Promise<string> {
    return await this.enderecoSelect.element(by.css('option:checked')).getText();
  }

  async usuarioSelectLastOption(): Promise<void> {
    await this.usuarioSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async usuarioSelectOption(option: string): Promise<void> {
    await this.usuarioSelect.sendKeys(option);
  }

  getUsuarioSelect(): ElementFinder {
    return this.usuarioSelect;
  }

  async getUsuarioSelectedOption(): Promise<string> {
    return await this.usuarioSelect.element(by.css('option:checked')).getText();
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

export class ResponsavelDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-responsavel-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-responsavel'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
