import { element, by, ElementFinder } from 'protractor';

export class HarasComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-haras div table .btn-danger'));
  title = element.all(by.css('jhi-haras div h2#page-heading span')).first();

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

export class HarasUpdatePage {
  pageTitle = element(by.id('jhi-haras-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nomeInput = element(by.id('field_nome'));
  localidadeInput = element(by.id('field_localidade'));
  ufInput = element(by.id('field_uf'));
  responsavelSelect = element(by.id('field_responsavel'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNomeInput(nome: string): Promise<void> {
    await this.nomeInput.sendKeys(nome);
  }

  async getNomeInput(): Promise<string> {
    return await this.nomeInput.getAttribute('value');
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

  async responsavelSelectLastOption(): Promise<void> {
    await this.responsavelSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async responsavelSelectOption(option: string): Promise<void> {
    await this.responsavelSelect.sendKeys(option);
  }

  getResponsavelSelect(): ElementFinder {
    return this.responsavelSelect;
  }

  async getResponsavelSelectedOption(): Promise<string> {
    return await this.responsavelSelect.element(by.css('option:checked')).getText();
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

export class HarasDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-haras-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-haras'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
