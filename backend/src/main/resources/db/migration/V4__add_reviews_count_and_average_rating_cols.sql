ALTER TABLE books
ADD COLUMN reviews_count BIGINT DEFAULT 0,
ADD COLUMN average_rating DECIMAL(3,2) DEFAULT 0.00;

DELIMITER //

CREATE TRIGGER trg_reviews_after_insert
    AFTER INSERT ON reviews
    FOR EACH ROW
BEGIN
    UPDATE books
    SET
        average_rating = ((average_rating * reviews_count) + NEW.rate) / (reviews_count + 1),
        reviews_count = reviews_count + 1
    WHERE id = NEW.book_id;
END //

CREATE TRIGGER trg_reviews_after_update
    AFTER UPDATE ON reviews
    FOR EACH ROW
BEGIN
    UPDATE books
    SET
        average_rating = ((average_rating * reviews_count) - OLD.rate + NEW.rate) / reviews_count
    WHERE id = NEW.book_id;
END //

CREATE TRIGGER trg_reviews_after_delete
    AFTER DELETE ON reviews
    FOR EACH ROW
BEGIN
    UPDATE books
    SET
        average_rating = CASE
                             WHEN reviews_count - 1 <= 0 THEN 0
                             ELSE ((average_rating * reviews_count) - OLD.rate) / (reviews_count - 1)
                         END,
        reviews_count = reviews_count - 1
    WHERE id = OLD.book_id;
END //

DELIMITER ;
