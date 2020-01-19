import { element, by, ElementFinder } from 'protractor';

export class LocalComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-local div table .btn-danger'));
  title = element.all(by.css('jhi-local div h2#page-heading span')).first();

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

export class LocalUpdatePage {
  pageTitle = element(by.id('jhi-local-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nomeInput = element(by.id('field_nome'));
  areaInput = element(by.id('field_area'));
  ehContiguaInput = element(by.id('field_ehContigua'));
  tipoSelect = element(by.id('field_tipo'));
  enderecoSelect = element(by.id('field_endereco'));
  paiSelect = element(by.id('field_pai'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNomeInput(nome: string): Promise<void> {
    await this.nomeInput.sendKeys(nome);
  }

  async getNomeInput(): Promise<string> {
    return await this.nomeInput.getAttribute('value');
  }

  async setAreaInput(area: string): Promise<void> {
    await this.areaInput.sendKeys(area);
  }

  async getAreaInput(): Promise<string> {
    return await this.areaInput.getAttribute('value');
  }

  getEhContiguaInput(): ElementFinder {
    return this.ehContiguaInput;
  }

  async tipoSelectLastOption(): Promise<void> {
    await this.tipoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async tipoSelectOption(option: string): Promise<void> {
    await this.tipoSelect.sendKeys(option);
  }

  getTipoSelect(): ElementFinder {
    return this.tipoSelect;
  }

  async getTipoSelectedOption(): Promise<string> {
    return await this.tipoSelect.element(by.css('option:checked')).getText();
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

  async paiSelectLastOption(): Promise<void> {
    await this.paiSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async paiSelectOption(option: string): Promise<void> {
    await this.paiSelect.sendKeys(option);
  }

  getPaiSelect(): ElementFinder {
    return this.paiSelect;
  }

  async getPaiSelectedOption(): Promise<string> {
    return await this.paiSelect.element(by.css('option:checked')).getText();
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

export class LocalDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-local-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-local'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
