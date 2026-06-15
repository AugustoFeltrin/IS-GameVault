/// <reference types="cypress" />

describe('Fluxo de uso do GameVault', () => {
  
  beforeEach(() => {
    cy.clearLocalStorage();
  });

  it('Deve fazer login, pesquisar um jogo, dar uma nota e verificar no diário', () => {
    cy.visit('/login');

    cy.get('input[type="email"]').type('cypress@gamevault.com');
    cy.get('input[type="password"]').type('CypVault26!');
    
    cy.get('button').contains('Entrar').click();

    cy.url().should('include', '/home');

    cy.get('input[placeholder="Buscar por títulos..."]').type('Hollow Knight');

    cy.wait(2000); 

    cy.get('div').contains('Hollow Knight').click();

    cy.get('h2').contains('Hollow Knight').should('be.visible');

    cy.get('[data-cy="star-5"]').click();

    cy.get('textarea[placeholder="Escreva sua análise (opcional)..."]')
      .type('Git Gud');

    cy.get('button').contains('Salvar no Diário').click();

    cy.contains('Avaliação salva com sucesso').should('be.visible');

    cy.get('nav').contains('Meu Diário').click();
    cy.url().should('include', '/profile');

    cy.contains('Hollow Knight').should('be.visible');
    cy.contains('Git Gud').should('be.visible');

    cy.get('[data-cy="review-rating"]').should('have.attr', 'data-rating', '5');

    cy.wait(2000); 

    cy.get('button').contains('Sair').click();
  });
});