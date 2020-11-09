-- carrega alguns registros para teste
-- registro utilizado em: RelacionalAutorizacaoRegistropessoalCovid19Test.java

insert into registro (
    id,
    naoPegueiCovid19,
    naoPegueiCovid19Data,
    naoSeiSePegueiCovid19,
    naoSeiSePegueiCovid19Data,
    pegueiCovid19FazMenos14Dias,
    pegueiCovid19FazMenos14DiasData,
    pegueiCovid19FazMais14Dias,
    pegueiCovid19FazMais14DiasData,
    curadoDeCovid19,
    curadoDeCovid19Data
)
values (
    '99fa309b-e362-440c-8812-af9df067dad3',
    true,
    '2020-09-01T14:51:15.983050-03:00',
    false,
    '2020-09-01T14:51:15.983050-03:00',
    true,
    '2020-09-01T14:51:15.983050-03:00',
    false,
    '2020-09-01T14:51:15.983050-03:00',
    true,
    '2020-09-01T14:51:15.983050-03:00'
);

insert into autorizacao (
    id,
    email,
    password,
    recadastrar,
    registro_id
)
values(
    '7604764b-c1a2-47f7-ac4e-c572a6f91928',
    'architect.tonena@gmail.com',
    'senha',
    false,
    '99fa309b-e362-440c-8812-af9df067dad3'
);