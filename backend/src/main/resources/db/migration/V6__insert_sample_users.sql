START TRANSACTION;

INSERT INTO addresses (id, country, city) VALUES (1, 'United States', 'New York');
INSERT INTO addresses (id, country, city) VALUES (2, 'United States', 'California');
INSERT INTO addresses (id, country, city) VALUES (3, 'China', 'Shanghai');
INSERT INTO addresses (id, country, city) VALUES (4, 'United Kingdom', 'London');
INSERT INTO addresses (id, country, city) VALUES (5, 'France', 'Paris');
INSERT INTO addresses (id, country, city) VALUES (6, 'Saudi Arabia', 'Riyadh');
INSERT INTO addresses (id, country, city) VALUES (7, 'Brazil', 'São Paulo');
INSERT INTO addresses (id, country, city) VALUES (8, 'Italy', 'Milan');
INSERT INTO addresses (id, country, city) VALUES (9, 'Japan', 'Tokyo');
INSERT INTO addresses (id, country, city) VALUES (10, 'Russia', 'Moscow');
INSERT INTO addresses (id, country, city) VALUES (11, 'Netherlands', 'Amsterdam');

INSERT INTO users (id, username, password, email, address_id) VALUES (1, 'admin', '$2b$12$AIAiUqG.05ywgyDeKLZrW.hde1qQZ8xuRclMZjqUw8ZkBB5.BZfdu', 'admin@domain.com', 1);
INSERT INTO users (id, username, password, email, address_id) VALUES (2, 'user', '$2b$12$YIaz2blx6ANMhKKtyp/dGOPYS/ih41NdTLpFQF6NdBMIm.j5jVYNW', 'user@example.com', 2);
INSERT INTO users (id, username, password, email, address_id) VALUES (3, 'li_wei', '$2b$12$ZkgD6sN2OR0VoCnvGoR/zurOHnBy6pWdanYCakpLiUqjkGMjvV51u', 'li.wei@example.cn', 3);
INSERT INTO users (id, username, password, email, address_id) VALUES (4, 'daniel_ross', '$2b$12$dRhwIOZGz6QtNZCcMTbfT.oVe3Yenrhnlrxv3o3rdpqg7i2NiG9Ki', 'daniel.ross@example.co.uk', 4);
INSERT INTO users (id, username, password, email, address_id) VALUES (5, 'amelie_duval', '$2b$12$2EkuKaEKUZzGNEYCVe77nO3gEyhl632X3joJMS.uHX9/hx28vfvN2', 'amelie.duval@example.fr', 5);
INSERT INTO users (id, username, password, email, address_id) VALUES (6, 'mohammed_fahd', '$2b$12$0dbHY.A.jKRaRiqinBGLK.K3iZziWbTka2FSH.1kHBwiroYeTlSve', 'mohammed.fahd@example.sa', 6);
INSERT INTO users (id, username, password, email, address_id) VALUES (7, 'lucas_silva', '$2b$12$DoOWgwhEkS1GloNwP3Kxu.2X.7xJehQzfTm3fOBh3Gte3IUaQjUzO', 'lucas.silva@example.br', 7);
INSERT INTO users (id, username, password, email, address_id) VALUES (8, 'sofia_rinaldi', '$2b$12$0.uj52j3.EfLU9yqlRW9Pe.aOotb62olSBWAuGOt1MHzRUMpW95AC', 'sofia.rinaldi@example.it', 8);
INSERT INTO users (id, username, password, email, address_id) VALUES (9, 'akira_tanaka', '$2b$12$t47dq2B.vWRORSFAOH1o6.iRvqHRz/HNov/2bsMR3Q/QcNrKFZFWe', 'akira.tanaka@example.jp', 9);
INSERT INTO users (id, username, password, email, address_id) VALUES (10, 'irina_petrov', '$2b$12$msM5Lr.z6.B6TI6tcNLQsuKKq0.t6RC4bNf1hZC7PAsZGM.dag8sK', 'irina.petrov@example.ru', 10);
INSERT INTO users (id, username, password, email, address_id) VALUES (11, 'noah_jansen', '$2b$12$fKsVEYwF0cv3mc8yxG8s2ej/MygmgWwVfXJJpaSf5P4WR1voZENAG', 'noah.jansen@example.nl', 11);

INSERT INTO customers (id) VALUES (2), (3), (4), (5), (6), (7), (8), (9), (10), (11);
INSERT INTO admins (id) VALUES (1);

INSERT INTO wallets (customer_id, balance) VALUES (2, 5000);
INSERT INTO wallets (customer_id, balance) VALUES (3, 5000);
INSERT INTO wallets (customer_id, balance) VALUES (4, 5000);
INSERT INTO wallets (customer_id, balance) VALUES (5, 5000);
INSERT INTO wallets (customer_id, balance) VALUES (6, 5000);
INSERT INTO wallets (customer_id, balance) VALUES (7, 5000);
INSERT INTO wallets (customer_id, balance) VALUES (8, 5000);
INSERT INTO wallets (customer_id, balance) VALUES (9, 5000);
INSERT INTO wallets (customer_id, balance) VALUES (10, 5000);
INSERT INTO wallets (customer_id, balance) VALUES (11, 5000);

INSERT INTO carts (customer_id) VALUES (2);
INSERT INTO carts (customer_id) VALUES (3);
INSERT INTO carts (customer_id) VALUES (4);
INSERT INTO carts (customer_id) VALUES (5);
INSERT INTO carts (customer_id) VALUES (6);
INSERT INTO carts (customer_id) VALUES (7);
INSERT INTO carts (customer_id) VALUES (8);
INSERT INTO carts (customer_id) VALUES (9);
INSERT INTO carts (customer_id) VALUES (10);
INSERT INTO carts (customer_id) VALUES (11);

COMMIT;