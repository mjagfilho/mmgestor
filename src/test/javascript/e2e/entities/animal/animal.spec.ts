import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AnimalComponentsPage, AnimalDeleteDialog, AnimalUpdatePage } from './animal.page-object';

const expect = chai.expect;

describe('Animal e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let animalComponentsPage: AnimalComponentsPage;
  let animalUpdatePage: AnimalUpdatePage;
  let animalDeleteDialog: AnimalDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Animals', async () => {
    await navBarPage.goToEntity('animal');
    animalComponentsPage = new AnimalComponentsPage();
    await browser.wait(ec.visibilityOf(animalComponentsPage.title), 5000);
    expect(await animalComponentsPage.getTitle()).to.eq('mmGestorApp.animal.home.title');
  });

  it('should load create Animal page', async () => {
    await animalComponentsPage.clickOnCreateButton();
    animalUpdatePage = new AnimalUpdatePage();
    expect(await animalUpdatePage.getPageTitle()).to.eq('mmGestorApp.animal.home.createOrEditLabel');
    await animalUpdatePage.cancel();
  });

  it('should create and save Animals', async () => {
    const nbButtonsBeforeCreate = await animalComponentsPage.countDeleteButtons();

    await animalComponentsPage.clickOnCreateButton();
    await promise.all([
      animalUpdatePage.setNomeInput('nome'),
      animalUpdatePage.setDtNascimentoInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      animalUpdatePage.sexoSelectLastOption(),
      animalUpdatePage.pelagemSelectLastOption(),
      animalUpdatePage.dadosAssociacaoSelectLastOption()
    ]);
    expect(await animalUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await animalUpdatePage.getDtNascimentoInput()).to.contain(
      '2001-01-01T02:30',
      'Expected dtNascimento value to be equals to 2000-12-31'
    );
    const selectedEhVivo = animalUpdatePage.getEhVivoInput();
    if (await selectedEhVivo.isSelected()) {
      await animalUpdatePage.getEhVivoInput().click();
      expect(await animalUpdatePage.getEhVivoInput().isSelected(), 'Expected ehVivo not to be selected').to.be.false;
    } else {
      await animalUpdatePage.getEhVivoInput().click();
      expect(await animalUpdatePage.getEhVivoInput().isSelected(), 'Expected ehVivo to be selected').to.be.true;
    }
    await animalUpdatePage.save();
    expect(await animalUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await animalComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Animal', async () => {
    const nbButtonsBeforeDelete = await animalComponentsPage.countDeleteButtons();
    await animalComponentsPage.clickOnLastDeleteButton();

    animalDeleteDialog = new AnimalDeleteDialog();
    expect(await animalDeleteDialog.getDialogTitle()).to.eq('mmGestorApp.animal.delete.question');
    await animalDeleteDialog.clickOnConfirmButton();

    expect(await animalComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
