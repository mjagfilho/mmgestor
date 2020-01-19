import { element, by, ElementFinder } from 'protractor';

export class ClienteFornecedorComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-cliente-fornecedor div table .btn-danger'));
  title = element.all(by.css('jhi-cliente-fornecedor div h2#page-heading span')).first();

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

export class ClienteFornecedorUpdatePage {
  pageTitle = element(by.id('jhi-cliente-fornecedor-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nomeInput = element(by.id('field_nome'));
  dtNascimentoInput = element(by.id('field_dtNascimento'));
  cpfInput = element(by.id('field_cpf'));
  nomeHarasInput = element(by.id('field_nomeHaras'));
  localidadeHarasInput = element(by.id('field_localidadeHaras'));
  ufHarasInput = element(by.id('field_ufHaras'));
  enderecoSelect = element(by.id('field_endereco'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNomeInput(nome: string): Promise<void> {
    await this.nomeInput.sendKeys(nome);
  }

  async getNomeInput(): Promise<string> {
    return await this.nomeInput.getAttribute('value');
  }

  async setDtNascimentoInput(dtNascimento: string): Promise<void> {
    await this.dtNascimentoInput.sendKeys(dtNascimento);
  }

  async getDtNascimentoInput(): Promise<string> {
    return await this.dtNascimentoInput.getAttribute('value');
  }

  async setCpfInput(cpf: string): Promise<void> {
    await this.cpfInput.sendKeys(cpf);
  }

  async getCpfInput(): Promise<string> {
    return await this.cpfInput.getAttribute('value');
  }

  async setNomeHarasInput(nomeHaras: string): Promise<void> {
    await this.nomeHarasInput.sendKeys(nomeHaras);
  }

  async getNomeHarasInput(): Promise<string> {
    return await this.nomeHarasInput.getAttribute('value');
  }

  async setLocalidadeHarasInput(localidadeHaras: string): Promise<void> {
    await this.localidadeHarasInput.sendKeys(localidadeHaras);
  }

  async getLocalidadeHarasInput(): Promise<string> {
    return await this.localidadeHarasInput.getAttribute('value');
  }

  async setUfHarasInput(ufHaras: string): Promise<void> {
    await this.ufHarasInput.sendKeys(ufHaras);
  }

  async getUfHarasInput(): Promise<string> {
    return await this.ufHarasInput.getAttribute('value');
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

export class ClienteFornecedorDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-clienteFornecedor-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-clienteFornecedor'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
