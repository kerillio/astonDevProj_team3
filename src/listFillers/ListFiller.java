package listFillers;

import catalogs.Catalog;
import models.ICustomModel;

public interface ListFiller<T extends ICustomModel> {
    Catalog<T> fileFiller(int size);

    Catalog<T>  randomFiller(int size);

    int countLines();
}
