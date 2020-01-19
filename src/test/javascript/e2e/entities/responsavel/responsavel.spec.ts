import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  ResponsavelComponentsPage,
  /* ResponsavelDeleteDialog,
   */ ResponsavelUpdatePage
} from './responsavel.page-object';

const expect = chai.expect;

describe('Responsavel e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let responsavelComponentsPage: ResponsavelComponentsPage;
  let responsavelUpdatePage: ResponsavelUpdatePage;
  /* let responsavelDeleteDialog: ResponsavelDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Responsavels', async () => {
    await navBarPage.goToEntity('responsavel');
    responsavelComponentsPage = new ResponsavelComponentsPage();
    await browser.wait(ec.visibilityOf(responsavelComponentsPage.title), 5000);
    expect(await responsavelComponentsPage.getTitle()).to.eq('mmGestorApp.responsavel.home.title');
  });

  it('should load create Responsavel page', async () => {
    await responsavelComponentsPage.clickOnCreateButton();
    responsavelUpdatePage = new ResponsavelUpdatePage();
    expect(await responsavelUpdatePage.getPageTitle()).to.eq('mmGestorApp.responsavel.home.createOrEditLabel');
    await responsavelUpdatePage.cancel();
  });

  /*  it('should create and save Responsavels', async () => {
        const nbButtonsBeforeCreate = await responsavelComponentsPage.countDeleteButtons();

        await responsavelComponentsPage.clickOnCreateButton();
        await promise.all([
            responsavelUpdatePage.setCpfInput('5'),
            responsavelUpdatePage.setNomeCompletoInput('nomeCompleto'),
            responsavelUpdatePage.setDtNascimentoInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            responsavelUpdatePage.enderecoSelectLastOption(),
            responsavelUpdatePage.usuarioSelectLastOption(),
        ]);
        expect(await responsavelUpdatePage.getCpfInput()).to.eq('5', 'Expected cpf value to be equals to 5');
        expect(await responsavelUpdatePage.getNomeCompletoInput()).to.eq('nomeCompleto', 'Expected NomeCompleto value to be equals to nomeCompleto');
        expect(await responsavelUpdatePage.getDtNascimentoInput()).to.contain('2001-01-01T02:30', 'Expected dtNascimento value to be equals to 2000-12-31');
        await responsavelUpdatePage.save();
        expect(await responsavelUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await responsavelComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last Responsavel', async () => {
        const nbButtonsBeforeDelete = await responsavelComponentsPage.countDeleteButtons();
        await responsavelComponentsPage.clickOnLastDeleteButton();

        responsavelDeleteDialog = new ResponsavelDeleteDialog();
        expect(await responsavelDeleteDialog.getDialogTitle())
            .to.eq('mmGestorApp.responsavel.delete.question');
        await responsavelDeleteDialog.clickOnConfirmButton();

        expect(await responsavelComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
