import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EnderecoComponentsPage, EnderecoDeleteDialog, EnderecoUpdatePage } from './endereco.page-object';

const expect = chai.expect;

describe('Endereco e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let enderecoComponentsPage: EnderecoComponentsPage;
  let enderecoUpdatePage: EnderecoUpdatePage;
  let enderecoDeleteDialog: EnderecoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Enderecos', async () => {
    await navBarPage.goToEntity('endereco');
    enderecoComponentsPage = new EnderecoComponentsPage();
    await browser.wait(ec.visibilityOf(enderecoComponentsPage.title), 5000);
    expect(await enderecoComponentsPage.getTitle()).to.eq('mmGestorApp.endereco.home.title');
  });

  it('should load create Endereco page', async () => {
    await enderecoComponentsPage.clickOnCreateButton();
    enderecoUpdatePage = new EnderecoUpdatePage();
    expect(await enderecoUpdatePage.getPageTitle()).to.eq('mmGestorApp.endereco.home.createOrEditLabel');
    await enderecoUpdatePage.cancel();
  });

  it('should create and save Enderecos', async () => {
    const nbButtonsBeforeCreate = await enderecoComponentsPage.countDeleteButtons();

    await enderecoComponentsPage.clickOnCreateButton();
    await promise.all([
      enderecoUpdatePage.setCepInput('5'),
      enderecoUpdatePage.setLogradouroInput('logradouro'),
      enderecoUpdatePage.setComplementoInput('complemento'),
      enderecoUpdatePage.setBairroInput('bairro'),
      enderecoUpdatePage.setLocalidadeInput('localidade'),
      enderecoUpdatePage.setUfInput('uf')
    ]);
    expect(await enderecoUpdatePage.getCepInput()).to.eq('5', 'Expected cep value to be equals to 5');
    expect(await enderecoUpdatePage.getLogradouroInput()).to.eq('logradouro', 'Expected Logradouro value to be equals to logradouro');
    expect(await enderecoUpdatePage.getComplementoInput()).to.eq('complemento', 'Expected Complemento value to be equals to complemento');
    expect(await enderecoUpdatePage.getBairroInput()).to.eq('bairro', 'Expected Bairro value to be equals to bairro');
    expect(await enderecoUpdatePage.getLocalidadeInput()).to.eq('localidade', 'Expected Localidade value to be equals to localidade');
    expect(await enderecoUpdatePage.getUfInput()).to.eq('uf', 'Expected Uf value to be equals to uf');
    await enderecoUpdatePage.save();
    expect(await enderecoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await enderecoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Endereco', async () => {
    const nbButtonsBeforeDelete = await enderecoComponentsPage.countDeleteButtons();
    await enderecoComponentsPage.clickOnLastDeleteButton();

    enderecoDeleteDialog = new EnderecoDeleteDialog();
    expect(await enderecoDeleteDialog.getDialogTitle()).to.eq('mmGestorApp.endereco.delete.question');
    await enderecoDeleteDialog.clickOnConfirmButton();

    expect(await enderecoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
