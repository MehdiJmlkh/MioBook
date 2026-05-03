import AuthorIcon from "../../assets/author-icon.svg";
import AuthorImage from "../../assets/author.svg";
import { useAuthors } from "../../queries/useAuthors";
import Card from "../Card";
import Table from "../Table";
import "./AuthorsCard.css";

const AuthorsCard = () => {
  const { data: authors } = useAuthors();

  return (
    <Card title="Authors" icon={<img src={AuthorIcon} alt="" />}>
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
                <img className="table-image" src={AuthorImage} alt="" />
              </td>
              <td data-label="Name">{author.name}</td>
              <td data-label="Pen Name">{author.penName}</td>
              <td data-label="Nationality">{author.nationality}</td>
              <td data-label="Born">{author.born}</td>
              <td data-label="Died">{author.died || <>&mdash;</>}</td>
            </tr>
          ))}
        </tbody>
      </Table>
    </Card>
  );
};

export default AuthorsCard;
