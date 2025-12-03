INSERT INTO categories (id, name, description, active)
VALUES (1, 'Geral', 'Categoria Padrão do Sistema', true)
ON CONFLICT (id) DO NOTHING;

SELECT setval('categories_id_seq', COALESCE((SELECT MAX(id)+1 FROM categories), 1), false);