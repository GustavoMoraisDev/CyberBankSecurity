# 🛡️ CyberBank Security

# Objetivo do Documento
Documento criado para auxiliar no **planejamento e desenvolvimento do Projeto de Antifraude Bancária**, com foco em transações via **PIX**.

---

# Propósito

# Problema
De acordo com fontes como o **Banco Central**, o uso crescente do **PIX** tem sido acompanhado por um aumento alarmante nas **fraudes e golpes**.

# Impacto
Essas fraudes geram:
- Perdas financeiras significativas para as vítimas;  
- Crise de confiança no sistema de pagamento instantâneo;  
- Aumento de custos operacionais para instituições bancárias (atendimento, ressarcimentos, etc.);  
- Danos reputacionais à marca.

# Oportunidade
Desenvolver um **Sistema de Antifraude em tempo real** capaz de:
- Detectar, alertar e mitigar transações suspeitas;  
- Proteger o capital das instituições financeiras;  
- Aumentar a segurança e confiança dos clientes.

---

# Produto / Escopo

# Produto
Software voltado à **detecção e prevenção de fraudes em transações via PIX**, utilizando **análise comportamental** e **monitoramento em tempo real**, promovendo maior segurança e confiabilidade ao sistema bancário digital.

---

# Funcionalidades

# Para o Cliente
- [ ] Interface bancária simulando a criação de uma conta bancária;  
- [ ] Cada usuário possui um número de conta único e senha personalizada;  
- [ ] Login individual com acesso às funcionalidades bancárias;  
- [ ] Exibição de saldo em conta;  
- [ ] Funcionalidade de **PIX** entre usuários com chaves cadastradas.

# Para Bancos, Instituições Financeiras e Fintechs
- [ ] Validação de Documentos
- [ ] Validação de Fotos pelo Compliance
- [ ] Dashboard com **monitoramento ao vivo** das transações;  
- [ ] Exibição em tempo real do **valor prevenido de fraudes**;  
- [ ] **Bloqueio manual de contas** suspeitas;  
- [ ] Opção de **ativar/desativar o software**;  
- [ ] Criação e configuração de **novas regras personalizadas de segurança**.

# Funcionalidades do Software (Core)
- [ ] **Motor de Análise Comportamental (Machine Learning)**:  
  Analisa histórico de transações, horários habituais, valores médios, contatos frequentes e perfil de uso para criar um **perfil de risco individualizado**.  
- [ ] **Ação de Alerta**:  
  Sinaliza transações que se desviem do comportamento normal do usuário (ex: alto valor, nova localização, horário incomum).  
  Solicita autenticação adicional antes de confirmar o PIX.  
- [ ] **Sistema de Regras Heurísticas e Listas Negras**:  
  Verifica transações contra **regras de negócio** e **listas de chaves PIX reportadas** como fraudulentas.  
- [ ] **Ação de Bloqueio Automático**:  
  Bloqueia transações de **alto risco** imediato (ex: chave em blacklist ou valor acima do limite).  
- [ ] **Notificações em Tempo Real**:  
  Informa **usuário e banco** sobre qualquer ação de alerta, bloqueio ou análise.

---

# Equipe

| Função | Nome | Responsabilidade |
|--------|------|------------------|
| Arquiteto do Software & Back-End Developer | **Gustavo Félix Morais** | Arquitetura do sistema e desenvolvimento da API |
| Front-End Developer | **Thiago Rocha** | Desenvolvimento da interface bancária e dashboard |
| DevOps / Infraestrutura | **Geovanny Wilson** | Ambiente de desenvolvimento e repositório |
| DBA | **Rômulo Giardini** | Administração e modelagem do banco de dados |
|  QA Tester | **Yasmim Leal** | Garantia de qualidade, testes e validação das entregas |

---

# Licença
Este projeto é de uso interno e educacional. Todos os direitos reservados à equipe **CyberBank Security**.

