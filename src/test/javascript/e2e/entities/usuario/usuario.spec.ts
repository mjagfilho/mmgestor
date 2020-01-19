import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { UsuarioComponentsPage, UsuarioDeleteDialog, UsuarioUpdatePage } from './usuario.page-object';

const expect = chai.expect;

describe('Usuario e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let usuarioComponentsPage: UsuarioComponentsPage;
  let usuarioUpdatePage: UsuarioUpdatePage;
  let usuarioDeleteDialog: UsuarioDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Usuarios', async () => {
    await navBarPage.goToEntity('usuario');
    usuarioComponentsPage = new UsuarioComponentsPage();
    await browser.wait(ec.visibilityOf(usuarioComponentsPage.title), 5000);
    expect(await usuarioComponentsPage.getTitle()).to.eq('mmGestorApp.usuario.home.title');
  });

  it('should load create Usuario page', async () => {
    await usuarioComponentsPage.clickOnCreateButton();
    usuarioUpdatePage = new UsuarioUpdatePage();
    expect(await usuarioUpdatePage.getPageTitle()).to.eq('mmGestorApp.usuario.home.createOrEditLabel');
    await usuarioUpdatePage.cancel();
  });

  it('should create and save Usuarios', async () => {
    const nbButtonsBeforeCreate = await usuarioComponentsPage.countDeleteButtons();

    await usuarioComponentsPage.clickOnCreateButton();
    await promise.all([usuarioUpdatePage.setEmailInput('email'), usuarioUpdatePage.setSenhaInput('senha')]);
    expect(await usuarioUpdatePage.getEmailInput()).to.eq('email', 'Expected Email value to be equals to email');
    expect(await usuarioUpdatePage.getSenhaInput()).to.eq('senha', 'Expected Senha value to be equals to senha');
    await usuarioUpdatePage.save();
    expect(await usuarioUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await usuarioComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Usuario', async () => {
    const nbButtonsBeforeDelete = await usuarioComponentsPage.countDeleteButtons();
    await usuarioComponentsPage.clickOnLastDeleteButton();

    usuarioDeleteDialog = new UsuarioDeleteDialog();
    expect(await usuarioDeleteDialog.getDialogTitle()).to.eq('mmGestorApp.usuario.delete.question');
    await usuarioDeleteDialog.clickOnConfirmButton();

    expect(await usuarioComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
