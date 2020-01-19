import { element, by, ElementFinder } from 'protractor';

export class AnimalComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-animal div table .btn-danger'));
  title = element.all(by.css('jhi-animal div h2#page-heading span')).first();

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

export class AnimalUpdatePage {
  pageTitle = element(by.id('jhi-animal-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nomeInput = element(by.id('field_nome'));
  dtNascimentoInput = element(by.id('field_dtNascimento'));
  sexoSelect = element(by.id('field_sexo'));
  pelagemSelect = element(by.id('field_pelagem'));
  ehVivoInput = element(by.id('field_ehVivo'));
  dadosAssociacaoSelect = element(by.id('field_dadosAssociacao'));

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

  async setSexoSelect(sexo: string): Promise<void> {
    await this.sexoSelect.sendKeys(sexo);
  }

  async getSexoSelect(): Promise<string> {
    return await this.sexoSelect.element(by.css('option:checked')).getText();
  }

  async sexoSelectLastOption(): Promise<void> {
    await this.sexoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setPelagemSelect(pelagem: string): Promise<void> {
    await this.pelagemSelect.sendKeys(pelagem);
  }

  async getPelagemSelect(): Promise<string> {
    return await this.pelagemSelect.element(by.css('option:checked')).getText();
  }

  async pelagemSelectLastOption(): Promise<void> {
    await this.pelagemSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  getEhVivoInput(): ElementFinder {
    return this.ehVivoInput;
  }

  async dadosAssociacaoSelectLastOption(): Promise<void> {
    await this.dadosAssociacaoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async dadosAssociacaoSelectOption(option: string): Promise<void> {
    await this.dadosAssociacaoSelect.sendKeys(option);
  }

  getDadosAssociacaoSelect(): ElementFinder {
    return this.dadosAssociacaoSelect;
  }

  async getDadosAssociacaoSelectedOption(): Promise<string> {
    return await this.dadosAssociacaoSelect.element(by.css('option:checked')).getText();
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

export class AnimalDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-animal-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-animal'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
