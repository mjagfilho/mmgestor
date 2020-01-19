import { element, by, ElementFinder } from 'protractor';

export class EnderecoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-endereco div table .btn-danger'));
  title = element.all(by.css('jhi-endereco div h2#page-heading span')).first();

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

export class EnderecoUpdatePage {
  pageTitle = element(by.id('jhi-endereco-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  cepInput = element(by.id('field_cep'));
  logradouroInput = element(by.id('field_logradouro'));
  complementoInput = element(by.id('field_complemento'));
  bairroInput = element(by.id('field_bairro'));
  localidadeInput = element(by.id('field_localidade'));
  ufInput = element(by.id('field_uf'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCepInput(cep: string): Promise<void> {
    await this.cepInput.sendKeys(cep);
  }

  async getCepInput(): Promise<string> {
    return await this.cepInput.getAttribute('value');
  }

  async setLogradouroInput(logradouro: string): Promise<void> {
    await this.logradouroInput.sendKeys(logradouro);
  }

  async getLogradouroInput(): Promise<string> {
    return await this.logradouroInput.getAttribute('value');
  }

  async setComplementoInput(complemento: string): Promise<void> {
    await this.complementoInput.sendKeys(complemento);
  }

  async getComplementoInput(): Promise<string> {
    return await this.complementoInput.getAttribute('value');
  }

  async setBairroInput(bairro: string): Promise<void> {
    await this.bairroInput.sendKeys(bairro);
  }

  async getBairroInput(): Promise<string> {
    return await this.bairroInput.getAttribute('value');
  }

  async setLocalidadeInput(localidade: string): Promise<void> {
    await this.localidadeInput.sendKeys(localidade);
  }

  async getLocalidadeInput(): Promise<string> {
    return await this.localidadeInput.getAttribute('value');
  }

  async setUfInput(uf: string): Promise<void> {
    await this.ufInput.sendKeys(uf);
  }

  async getUfInput(): Promise<string> {
    return await this.ufInput.getAttribute('value');
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

export class EnderecoDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-endereco-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-endereco'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
