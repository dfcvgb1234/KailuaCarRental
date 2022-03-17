package system;

import rest.HttpHelper;
import sql.SqlController;
import sql.SqlParameter;
import sql.components.RentalContract;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        new SqlController("root", "Admin123").performSQLSelect("sahdkasjdhasdh", new SqlParameter<RentalContract>(new RentalContract(), RentalContract.class));
    }
}
