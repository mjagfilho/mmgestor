import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TipoLocalComponentsPage, TipoLocalDeleteDialog, TipoLocalUpdatePage } from './tipo-local.page-object';

const expect = chai.expect;

describe('TipoLocal e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let tipoLocalComponentsPage: TipoLocalComponentsPage;
  let tipoLocalUpdatePage: TipoLocalUpdatePage;
  let tipoLocalDeleteDialog: TipoLocalDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TipoLocals', async () => {
    await navBarPage.goToEntity('tipo-local');
    tipoLocalComponentsPage = new TipoLocalComponentsPage();
    await browser.wait(ec.visibilityOf(tipoLocalComponentsPage.title), 5000);
    expect(await tipoLocalComponentsPage.getTitle()).to.eq('mmGestorApp.tipoLocal.home.title');
  });

  it('should load create TipoLocal page', async () => {
    await tipoLocalComponentsPage.clickOnCreateButton();
    tipoLocalUpdatePage = new TipoLocalUpdatePage();
    expect(await tipoLocalUpdatePage.getPageTitle()).to.eq('mmGestorApp.tipoLocal.home.createOrEditLabel');
    await tipoLocalUpdatePage.cancel();
  });

  it('should create and save TipoLocals', async () => {
    const nbButtonsBeforeCreate = await tipoLocalComponentsPage.countDeleteButtons();

    await tipoLocalComponentsPage.clickOnCreateButton();
    await promise.all([tipoLocalUpdatePage.setNomeInput('nome'), tipoLocalUpdatePage.setDescricaoInput('descricao')]);
    expect(await tipoLocalUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await tipoLocalUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    await tipoLocalUpdatePage.save();
    expect(await tipoLocalUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await tipoLocalComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last TipoLocal', async () => {
    const nbButtonsBeforeDelete = await tipoLocalComponentsPage.countDeleteButtons();
    await tipoLocalComponentsPage.clickOnLastDeleteButton();

    tipoLocalDeleteDialog = new TipoLocalDeleteDialog();
    expect(await tipoLocalDeleteDialog.getDialogTitle()).to.eq('mmGestorApp.tipoLocal.delete.question');
    await tipoLocalDeleteDialog.clickOnConfirmButton();

    expect(await tipoLocalComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
