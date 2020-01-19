import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DadosAssociacaoComponentsPage, DadosAssociacaoDeleteDialog, DadosAssociacaoUpdatePage } from './dados-associacao.page-object';

const expect = chai.expect;

describe('DadosAssociacao e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let dadosAssociacaoComponentsPage: DadosAssociacaoComponentsPage;
  let dadosAssociacaoUpdatePage: DadosAssociacaoUpdatePage;
  let dadosAssociacaoDeleteDialog: DadosAssociacaoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load DadosAssociacaos', async () => {
    await navBarPage.goToEntity('dados-associacao');
    dadosAssociacaoComponentsPage = new DadosAssociacaoComponentsPage();
    await browser.wait(ec.visibilityOf(dadosAssociacaoComponentsPage.title), 5000);
    expect(await dadosAssociacaoComponentsPage.getTitle()).to.eq('mmGestorApp.dadosAssociacao.home.title');
  });

  it('should load create DadosAssociacao page', async () => {
    await dadosAssociacaoComponentsPage.clickOnCreateButton();
    dadosAssociacaoUpdatePage = new DadosAssociacaoUpdatePage();
    expect(await dadosAssociacaoUpdatePage.getPageTitle()).to.eq('mmGestorApp.dadosAssociacao.home.createOrEditLabel');
    await dadosAssociacaoUpdatePage.cancel();
  });

  it('should create and save DadosAssociacaos', async () => {
    const nbButtonsBeforeCreate = await dadosAssociacaoComponentsPage.countDeleteButtons();

    await dadosAssociacaoComponentsPage.clickOnCreateButton();
    await promise.all([
      dadosAssociacaoUpdatePage.setCriadorInput('criador'),
      dadosAssociacaoUpdatePage.setProprietarioInput('proprietario'),
      dadosAssociacaoUpdatePage.setLivroInput('livro'),
      dadosAssociacaoUpdatePage.setRegistroInput('registro'),
      dadosAssociacaoUpdatePage.setExameDNAInput('exameDNA'),
      dadosAssociacaoUpdatePage.setChipInput('chip')
    ]);
    expect(await dadosAssociacaoUpdatePage.getCriadorInput()).to.eq('criador', 'Expected Criador value to be equals to criador');
    expect(await dadosAssociacaoUpdatePage.getProprietarioInput()).to.eq(
      'proprietario',
      'Expected Proprietario value to be equals to proprietario'
    );
    expect(await dadosAssociacaoUpdatePage.getLivroInput()).to.eq('livro', 'Expected Livro value to be equals to livro');
    expect(await dadosAssociacaoUpdatePage.getRegistroInput()).to.eq('registro', 'Expected Registro value to be equals to registro');
    expect(await dadosAssociacaoUpdatePage.getExameDNAInput()).to.eq('exameDNA', 'Expected ExameDNA value to be equals to exameDNA');
    expect(await dadosAssociacaoUpdatePage.getChipInput()).to.eq('chip', 'Expected Chip value to be equals to chip');
    const selectedEhBloqueado = dadosAssociacaoUpdatePage.getEhBloqueadoInput();
    if (await selectedEhBloqueado.isSelected()) {
      await dadosAssociacaoUpdatePage.getEhBloqueadoInput().click();
      expect(await dadosAssociacaoUpdatePage.getEhBloqueadoInput().isSelected(), 'Expected ehBloqueado not to be selected').to.be.false;
    } else {
      await dadosAssociacaoUpdatePage.getEhBloqueadoInput().click();
      expect(await dadosAssociacaoUpdatePage.getEhBloqueadoInput().isSelected(), 'Expected ehBloqueado to be selected').to.be.true;
    }
    await dadosAssociacaoUpdatePage.save();
    expect(await dadosAssociacaoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await dadosAssociacaoComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last DadosAssociacao', async () => {
    const nbButtonsBeforeDelete = await dadosAssociacaoComponentsPage.countDeleteButtons();
    await dadosAssociacaoComponentsPage.clickOnLastDeleteButton();

    dadosAssociacaoDeleteDialog = new DadosAssociacaoDeleteDialog();
    expect(await dadosAssociacaoDeleteDialog.getDialogTitle()).to.eq('mmGestorApp.dadosAssociacao.delete.question');
    await dadosAssociacaoDeleteDialog.clickOnConfirmButton();

    expect(await dadosAssociacaoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
