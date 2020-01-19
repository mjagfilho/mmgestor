import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  ClienteFornecedorComponentsPage,
  /* ClienteFornecedorDeleteDialog,
   */ ClienteFornecedorUpdatePage
} from './cliente-fornecedor.page-object';

const expect = chai.expect;

describe('ClienteFornecedor e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let clienteFornecedorComponentsPage: ClienteFornecedorComponentsPage;
  let clienteFornecedorUpdatePage: ClienteFornecedorUpdatePage;
  /* let clienteFornecedorDeleteDialog: ClienteFornecedorDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ClienteFornecedors', async () => {
    await navBarPage.goToEntity('cliente-fornecedor');
    clienteFornecedorComponentsPage = new ClienteFornecedorComponentsPage();
    await browser.wait(ec.visibilityOf(clienteFornecedorComponentsPage.title), 5000);
    expect(await clienteFornecedorComponentsPage.getTitle()).to.eq('mmGestorApp.clienteFornecedor.home.title');
  });

  it('should load create ClienteFornecedor page', async () => {
    await clienteFornecedorComponentsPage.clickOnCreateButton();
    clienteFornecedorUpdatePage = new ClienteFornecedorUpdatePage();
    expect(await clienteFornecedorUpdatePage.getPageTitle()).to.eq('mmGestorApp.clienteFornecedor.home.createOrEditLabel');
    await clienteFornecedorUpdatePage.cancel();
  });

  /*  it('should create and save ClienteFornecedors', async () => {
        const nbButtonsBeforeCreate = await clienteFornecedorComponentsPage.countDeleteButtons();

        await clienteFornecedorComponentsPage.clickOnCreateButton();
        await promise.all([
            clienteFornecedorUpdatePage.setNomeInput('nome'),
            clienteFornecedorUpdatePage.setDtNascimentoInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            clienteFornecedorUpdatePage.setCpfInput('5'),
            clienteFornecedorUpdatePage.setNomeHarasInput('nomeHaras'),
            clienteFornecedorUpdatePage.setLocalidadeHarasInput('localidadeHaras'),
            clienteFornecedorUpdatePage.setUfHarasInput('ufHaras'),
            clienteFornecedorUpdatePage.enderecoSelectLastOption(),
        ]);
        expect(await clienteFornecedorUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
        expect(await clienteFornecedorUpdatePage.getDtNascimentoInput()).to.contain('2001-01-01T02:30', 'Expected dtNascimento value to be equals to 2000-12-31');
        expect(await clienteFornecedorUpdatePage.getCpfInput()).to.eq('5', 'Expected cpf value to be equals to 5');
        expect(await clienteFornecedorUpdatePage.getNomeHarasInput()).to.eq('nomeHaras', 'Expected NomeHaras value to be equals to nomeHaras');
        expect(await clienteFornecedorUpdatePage.getLocalidadeHarasInput()).to.eq('localidadeHaras', 'Expected LocalidadeHaras value to be equals to localidadeHaras');
        expect(await clienteFornecedorUpdatePage.getUfHarasInput()).to.eq('ufHaras', 'Expected UfHaras value to be equals to ufHaras');
        await clienteFornecedorUpdatePage.save();
        expect(await clienteFornecedorUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await clienteFornecedorComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last ClienteFornecedor', async () => {
        const nbButtonsBeforeDelete = await clienteFornecedorComponentsPage.countDeleteButtons();
        await clienteFornecedorComponentsPage.clickOnLastDeleteButton();

        clienteFornecedorDeleteDialog = new ClienteFornecedorDeleteDialog();
        expect(await clienteFornecedorDeleteDialog.getDialogTitle())
            .to.eq('mmGestorApp.clienteFornecedor.delete.question');
        await clienteFornecedorDeleteDialog.clickOnConfirmButton();

        expect(await clienteFornecedorComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
