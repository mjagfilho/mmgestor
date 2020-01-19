import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  HarasComponentsPage,
  /* HarasDeleteDialog,
   */ HarasUpdatePage
} from './haras.page-object';

const expect = chai.expect;

describe('Haras e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let harasComponentsPage: HarasComponentsPage;
  let harasUpdatePage: HarasUpdatePage;
  /* let harasDeleteDialog: HarasDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Haras', async () => {
    await navBarPage.goToEntity('haras');
    harasComponentsPage = new HarasComponentsPage();
    await browser.wait(ec.visibilityOf(harasComponentsPage.title), 5000);
    expect(await harasComponentsPage.getTitle()).to.eq('mmGestorApp.haras.home.title');
  });

  it('should load create Haras page', async () => {
    await harasComponentsPage.clickOnCreateButton();
    harasUpdatePage = new HarasUpdatePage();
    expect(await harasUpdatePage.getPageTitle()).to.eq('mmGestorApp.haras.home.createOrEditLabel');
    await harasUpdatePage.cancel();
  });

  /*  it('should create and save Haras', async () => {
        const nbButtonsBeforeCreate = await harasComponentsPage.countDeleteButtons();

        await harasComponentsPage.clickOnCreateButton();
        await promise.all([
            harasUpdatePage.setNomeInput('nome'),
            harasUpdatePage.setLocalidadeInput('localidade'),
            harasUpdatePage.setUfInput('uf'),
            harasUpdatePage.responsavelSelectLastOption(),
        ]);
        expect(await harasUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
        expect(await harasUpdatePage.getLocalidadeInput()).to.eq('localidade', 'Expected Localidade value to be equals to localidade');
        expect(await harasUpdatePage.getUfInput()).to.eq('uf', 'Expected Uf value to be equals to uf');
        await harasUpdatePage.save();
        expect(await harasUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await harasComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last Haras', async () => {
        const nbButtonsBeforeDelete = await harasComponentsPage.countDeleteButtons();
        await harasComponentsPage.clickOnLastDeleteButton();

        harasDeleteDialog = new HarasDeleteDialog();
        expect(await harasDeleteDialog.getDialogTitle())
            .to.eq('mmGestorApp.haras.delete.question');
        await harasDeleteDialog.clickOnConfirmButton();

        expect(await harasComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
