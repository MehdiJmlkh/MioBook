import { MdOutlineArticle } from "react-icons/md";
import Footer from "../../components/Footer";
import Header from "../../components/Header";
import "./BookContentPage.css";

const BookContentPage = () => {
  return (
    <body>
      <main>
        <div className="book-content__card book-content__heading">
            <MdOutlineArticle className="book-content__icon" />
            <h1 className="book-content__title">Book Name</h1>
            <span className="book-content__author">By Author Name</span>
        </div>
        <div className="book-content__card book-content__content">
          Lorem ipsum dolor sit amet consectetur adipisicing elit. Vero
          praesentium quae saepe laudantium illo dolores ipsam, nam dolorum rem
          optio fugiat eum quos laborum doloribus iure illum quia id cupiditate
          eos alias necessitatibus soluta temporibus? Rerum aliquam magni velit,
          incidunt fugiat alias minima nulla, aperiam tempora, reiciendis
          laborum harum? Nobis nam illo repellendus. Similique, praesentium.
          Perferendis nulla, praesentium mollitia eius eveniet repellendus totam
          animi molestias in esse similique illum vel tenetur nemo rem maiores
          provident! Quidem sit perferendis repellendus iste? Quo saepe deserunt
          architecto aut, ipsum, sint voluptas, explicabo ducimus ratione
          quisquam ut amet omnis accusamus. Libero quae earum rerum sint minus
          aliquid voluptatibus sed ducimus iste non distinctio veritatis porro
          tempore, repellat officia possimus vitae, facilis excepturi
          perspiciatis voluptas fuga provident eaque. Ad praesentium error
          consequatur obcaecati numquam similique molestias cupiditate illum
          doloremque cumque, dolores hic quam veritatis nulla voluptate
          voluptatem rerum nisi voluptates velit illo quae harum sunt nam!
          Labore, ab temporibus sequi quod fugit ad facere possimus laborum
          eligendi quis. Amet quis sint sunt numquam ab voluptas quisquam
          aperiam id, quidem reprehenderit illum vero temporibus magnam illo
          necessitatibus aliquam placeat eaque unde veritatis. Dolore obcaecati
          magnam asperiores modi repellat suscipit, debitis et itaque
          praesentium ducimus aperiam ex.
        </div>
      </main>
    </body>
  );
};

export default BookContentPage;
