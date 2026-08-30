import AuthorIcon from "../../assets/author-icon.svg";
import { useAuthors } from "../../queries/authors/useAuthors";
import Card from "../Card";
import EmptyIcon from "../EmptyIcon";
import Table from "../Table";
import NoResult from "../../assets/no-result.svg";
import "./AuthorsCard.css";
import Link from "../Link";
import NoImage from "../../assets/author.svg";

const AuthorsCard = () => {
  const { data: authors } = useAuthors();

  return (
    <Card title="Authors" icon={<img src={AuthorIcon} alt="" />}>
      {authors?.length === 0 ? (
        <EmptyIcon src={NoResult} description="No Author" />
      ) : (
        <Table>
          <thead>
            <tr>
              <th>Image</th>
              <th>Name</th>
              <th>Pen Name</th>
              <th>Nationality</th>
              <th>Born</th>
              <th>Died</th>
            </tr>
          </thead>
          <tbody>
            {authors?.map((author) => (
              <tr>
                <td className="table-image-row">
                  <Link className="link--padded" to={`/authors/${author.id}`}>
                    <img
                      className="table-image"
                      src={author.imageLink || NoImage}
                      alt=""
                      onError={(error) => {
                        error.currentTarget.onerror = null;
                        error.currentTarget.src = NoImage;
                      }}
                    />
                  </Link>
                </td>
                <td data-label="Name">
                  <Link className="link--padded" to={`/authors/${author.id}`}>
                    {author.name}
                  </Link>
                </td>
                <td data-label="Pen Name">{author.penName}</td>
                <td data-label="Nationality">{author.nationality}</td>
                <td data-label="Born">{author.born}</td>
                <td data-label="Died">{author.died || <>&mdash;</>}</td>
              </tr>
            ))}
          </tbody>
        </Table>
      )}
    </Card>
  );
};

export default AuthorsCard;
