import Card from "../Card";
import "./AuthorsCard.css";
import AuthorImage from "../../assets/author.svg";
import AuthorIcon from "../../assets/author-icon.svg";
import Price from "../Price";
import Table from "../Table";
const AuthorsCard = () => {
  return (
    <Card title="Books" icon={<img src={AuthorIcon} alt="" />}>
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
          <tr>
            <td className="table-image-row">
              <img className="table-image" src={AuthorImage} alt="" />
            </td>
            <td data-label="Name">Stephen King</td>
            <td data-label="Author">Richard Bachman</td>
            <td data-label="Nationality">American</td>
            <td data-label="Born">1947-09-21</td>
            <td data-label="Died">&mdash;</td>
          </tr>
          <tr>
            <td className="table-image-row">
              <img className="table-image" src={AuthorImage} alt="" />
            </td>
            <td data-label="Name">Stephen King</td>
            <td data-label="Author">Richard Bachman</td>
            <td data-label="Nationality">American</td>
            <td data-label="Born">1947-09-21</td>
            <td data-label="Died">&mdash;</td>
          </tr>
        </tbody>
      </Table>
    </Card>
  );
};

export default AuthorsCard;
