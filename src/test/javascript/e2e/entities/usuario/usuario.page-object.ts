import { element, by, ElementFinder } from 'protractor';

export class UsuarioComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-usuario div table .btn-danger'));
  title = element.all(by.css('jhi-usuario div h2#page-heading span')).first();

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

export class UsuarioUpdatePage {
  pageTitle = element(by.id('jhi-usuario-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  emailInput = element(by.id('field_email'));
  senhaInput = element(by.id('field_senha'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setEmailInput(email: string): Promise<void> {
    await this.emailInput.sendKeys(email);
  }

  async getEmailInput(): Promise<string> {
    return await this.emailInput.getAttribute('value');
  }

  async setSenhaInput(senha: string): Promise<void> {
    await this.senhaInput.sendKeys(senha);
  }

  async getSenhaInput(): Promise<string> {
    return await this.senhaInput.getAttribute('value');
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

export class UsuarioDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-usuario-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-usuario'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
