insert into users values(1, 'kamil', 'kamil', 'daszke', 'daszke', '01-01-1970', 'kamil@daszke.pl');

insert into section values(1, 'Ogród');
insert into section values(2, 'Motoryzacja');

insert into category values(1, 'Narzędzia', 1);
insert into category values(2, 'Kwiatki', 1);
insert into category values(3, 'Auta', 2);
insert into category values(4, 'Części', 2);

insert into auction values(1, 'VW Golf', 'super auto', 2, 2136.99, 3);
insert into auction values(2, 'Bratek', 'ladny kwiatek', 1, 21.37, 2);

insert into photo values(1, 1, 'photo1');
insert into photo values(2, 1, 'photo2');
insert into photo values(3, 2, 'photo3');

insert into parameter values(1, 'kolor');
insert into parameter values(2, 'przebieg');

insert into auction_parameter values(1, 1, 'czerwony');
insert into auction_parameter values(1, 2, '1488');
insert into auction_parameter values(2, 1, 'kolorowy')