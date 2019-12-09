package com.sputa.avarez.model;

public class DB
{

    public static String query="" +
            "CREATE TABLE  IF NOT EXISTS water(" +
            "AboneID varchar(255) NULL," +
            "ID varchar(255) NULL," +
            "FirstName varchar(255) NULL," +
            "LastName varchar(255) NULL," +
            "CellPhoneNumber float NULL," +
            "StartDate varchar(255) NULL," +
            "EndDate varchar(255) NULL," +
            "StartNumber float NULL," +
            "EndNumber float NULL," +
            "UsedWater float NULL," +
            "Fazelab float NULL," +
            "Tax float NULL," +
            "Budget float NULL," +
            "Debit float NULL," +
            "Amount float NULL" +
            ") ;" +
            "" +
            "INSERT INTO  water (AboneID, ID, FirstName, LastName, CellPhoneNumber, StartDate, EndDate, StartNumber, EndNumber, UsedWater, Fazelab, Tax, Budget, Debit, Amount) VALUES ('5615769', '0872026337', 'جواد ', 'هشیار', 9151885012, '1397/06/01', '1397/08/01', 6000, 7100, 1100, 120000, 12000, 1000, 0, 360000);" +
            "INSERT INTO  water (AboneID, ID, FirstName, LastName, CellPhoneNumber, StartDate, EndDate, StartNumber, EndNumber, UsedWater, Fazelab, Tax, Budget, Debit, Amount) VALUES ('5615087', '3820757430', 'حسین', 'پروین', 9188788205, '1397/06/01', '1397/08/01', 5200, 6300, 1100, 120000, 12000, 1000, 0, 360000);" +
            "INSERT INTO  water (AboneID, ID, FirstName, LastName, CellPhoneNumber, StartDate, EndDate, StartNumber, EndNumber, UsedWater, Fazelab, Tax, Budget, Debit, Amount) VALUES ('5611966', '6409955669', 'حجت ', 'قربانی ', 9144628878, '1397/06/01', '1397/08/01', 3000, 6200, 3200, 360000, 12000, 1000, 0, 1600000);" +
            "INSERT INTO  water (AboneID, ID, FirstName, LastName, CellPhoneNumber, StartDate, EndDate, StartNumber, EndNumber, UsedWater, Fazelab, Tax, Budget, Debit, Amount) VALUES ('5612352', '3810412996', 'حمید', 'نورافکن', 9388333686, '1397/06/01', '1397/08/01', 4100, 5000, 900, 120000, 12000, 1000, 0, 250000);" +
            "INSERT INTO  water (AboneID, ID, FirstName, LastName, CellPhoneNumber, StartDate, EndDate, StartNumber, EndNumber, UsedWater, Fazelab, Tax, Budget, Debit, Amount) VALUES ('5613272', '2899789082', 'سامان ', 'استحکام', 9143448721, '1397/06/01', '1397/08/01', 1200, 3200, 2000, 120000, 12000, 1000, 250000, 610000);" +
            "INSERT INTO  water (AboneID, ID, FirstName, LastName, CellPhoneNumber, StartDate, EndDate, StartNumber, EndNumber, UsedWater, Fazelab, Tax, Budget, Debit, Amount) VALUES ('5610731', '4172349420', 'فتح اله ', 'رادفر', 9141490359, '1397/06/01', '1397/08/01', 8000, 10100, 2100, 120000, 12000, 1000, 0, 750000);" +
            "INSERT INTO  water (AboneID, ID, FirstName, LastName, CellPhoneNumber, StartDate, EndDate, StartNumber, EndNumber, UsedWater, Fazelab, Tax, Budget, Debit, Amount) VALUES ('5610634', '6469166261', 'شهرام', 'نصری', 9128687817, '1397/06/01', '1397/08/01', 800, 1500, 700, 120000, 12000, 1000, 0, 370000);" +
            "INSERT INTO  water (AboneID, ID, FirstName, LastName, CellPhoneNumber, StartDate, EndDate, StartNumber, EndNumber, UsedWater, Fazelab, Tax, Budget, Debit, Amount) VALUES ('5612372', '3820636374', 'جمال', 'کریمی', 9188762795, '1397/06/01', '1397/08/01', 5600, 7100, 1500, 120000, 12000, 1000, 0, 960000);" +
            "INSERT INTO  water (AboneID, ID, FirstName, LastName, CellPhoneNumber, StartDate, EndDate, StartNumber, EndNumber, UsedWater, Fazelab, Tax, Budget, Debit, Amount) VALUES ('5615328', '2889951782', 'خضر', 'خضری', 9141672311, '1397/06/01', '1397/08/01', 300, 400, 100, 120000, 12000, 1000, 0, 310000);" +
            "INSERT INTO  water (AboneID, ID, FirstName, LastName, CellPhoneNumber, StartDate, EndDate, StartNumber, EndNumber, UsedWater, Fazelab, Tax, Budget, Debit, Amount) VALUES ('5613436', '5230043814', 'مجتبی ', 'کمالی', 9159250971, '1397/06/01', '1397/08/01', 6000, 7200, 1200, 120000, 12000, 1000, 0, 370000);" +
            "" +
            "CREATE TABLE  IF NOT EXISTS waste(" +
            "Parvandeh varchar(255) NULL," +
            "ID varchar(255) NULL," +
            "FirstName varchar(255) NULL," +
            "LastName varchar(255) NULL," +
            "CellPhoneNumber float NULL," +
            "LastPayDate float NULL," +
            "StartYear float NULL," +
            "EndYear float NULL," +
            "GroupName float NULL," +
            "Unit float NULL," +
            "Debit float NULL," +
            "Amount float NULL" +
            ") ;" +
            "" +
            "INSERT INTO  waste (Parvandeh, ID, FirstName, LastName, CellPhoneNumber, LastPayDate, StartYear, EndYear, GroupName, Unit, Debit, Amount) VALUES ('54639', '0872026337', 'جواد ', 'هشیار', 9151885012, '1396/05/25', 1397, 1397, 'تجاری', 1, 0, 360000);" +
            "INSERT INTO  waste (Parvandeh, ID, FirstName, LastName, CellPhoneNumber, LastPayDate, StartYear, EndYear, GroupName, Unit, Debit, Amount) VALUES ('51135', '3820757430', 'حسین', 'پروین', 9188788205, 0, 0, 0, 0, 0, 0, 0);" +
            "INSERT INTO  waste (Parvandeh, ID, FirstName, LastName, CellPhoneNumber, LastPayDate, StartYear, EndYear, GroupName, Unit, Debit, Amount) VALUES ('52785', '6409955669', 'حجت ', 'قربانی ', 9144628878, 0, 0, 0, 0, 0, 0, 0);" +
            "INSERT INTO  waste (Parvandeh, ID, FirstName, LastName, CellPhoneNumber, LastPayDate, StartYear, EndYear, GroupName, Unit, Debit, Amount) VALUES ('50788', '3810412996', 'حمید', 'نورافکن', 9388333686, '1396/05/25', 1397, 1397, 'تجاری', 1, 0, 360000);" +
            "INSERT INTO  waste (Parvandeh, ID, FirstName, LastName, CellPhoneNumber, LastPayDate, StartYear, EndYear, GroupName, Unit, Debit, Amount) VALUES ('55622', '2899789082', 'سامان ', 'استحکام', 9143448721, 0, 0, 0, 0, 0, 0, 0);" +
            "INSERT INTO  waste (Parvandeh, ID, FirstName, LastName, CellPhoneNumber, LastPayDate, StartYear, EndYear, GroupName, Unit, Debit, Amount) VALUES ('58334', '4172349420', 'فتح اله ', 'رادفر', 9141490359, 0, 0, 0, 0, 0, 0, 0);" +
            "INSERT INTO  waste (Parvandeh, ID, FirstName, LastName, CellPhoneNumber, LastPayDate, StartYear, EndYear, GroupName, Unit, Debit, Amount) VALUES ('53400', '6469166261', 'شهرام', 'نصری', 9128687817, 0, 0, 0, 0, 0, 0, 0);" +
            "INSERT INTO  waste (Parvandeh, ID, FirstName, LastName, CellPhoneNumber, LastPayDate, StartYear, EndYear, GroupName, Unit, Debit, Amount) VALUES ('57410', '3820636374', 'جمال', 'کریمی', 9188762795, 0, 0, 0, 0, 0, 0, 0);" +
            "INSERT INTO  waste (Parvandeh, ID, FirstName, LastName, CellPhoneNumber, LastPayDate, StartYear, EndYear, GroupName, Unit, Debit, Amount) VALUES ('58658', '2889951782', 'خضر', 'خضری', 9141672311, 0, 0, 0, 0, 0, 0, 0);" +
            "INSERT INTO  waste (Parvandeh, ID, FirstName, LastName, CellPhoneNumber, LastPayDate, StartYear, EndYear, GroupName, Unit, Debit, Amount) VALUES ('54315', '5230043814', 'مجتبی ', 'کمالی', 9159250971, '1396/05/25', 1397, 1397, 'تجاری', 1, 0, 360000);" +
            "" +
            "CREATE TABLE  IF NOT EXISTS power(" +
            "AboneID varchar(255) NULL," +
            "ID varchar(255) NULL," +
            "FirstName varchar(255) NULL," +
            "LastName varchar(255) NULL," +
            "CellPhoneNumber float NULL," +
            "StartDate varchar(255) NULL," +
            "EndDate varchar(255) NULL," +
            "StartNumber float NULL," +
            "EndNumber float NULL," +
            "UsedPower float NULL," +
            "Debit float NULL," +
            "Amount float NULL" +
            ");" +
            "" +
            "INSERT INTO  power (AboneID, ID, FirstName, LastName, CellPhoneNumber, StartDate, EndDate, StartNumber, EndNumber, UsedPower, Debit, Amount) VALUES ('2372598', '0872026337', 'جواد ', 'هشیار', 9151885012, '1397/06/02', '1397/08/02', 56000, 58000, 2000, 0, 620000);" +
            "INSERT INTO  power (AboneID, ID, FirstName, LastName, CellPhoneNumber, StartDate, EndDate, StartNumber, EndNumber, UsedPower, Debit, Amount) VALUES ('2386042', '3820757430', 'حسین', 'پروین', 9188788205, '1397/06/02', '1397/08/02', 48000, 58000, 10000, 0, 2300000);" +
            "INSERT INTO  power (AboneID, ID, FirstName, LastName, CellPhoneNumber, StartDate, EndDate, StartNumber, EndNumber, UsedPower, Debit, Amount) VALUES ('2374930', '6409955669', 'حجت ', 'قربانی ', 9144628878, '1397/06/02', '1397/08/02', 31000, 31200, 200, 0, 78000);" +
            "INSERT INTO  power (AboneID, ID, FirstName, LastName, CellPhoneNumber, StartDate, EndDate, StartNumber, EndNumber, UsedPower, Debit, Amount) VALUES ('2387287', '3810412996', 'حمید', 'نورافکن', 9388333686, '1397/06/02', '1397/08/02', 90000, 90900, 900, 0, 360000);" +
            "INSERT INTO  power (AboneID, ID, FirstName, LastName, CellPhoneNumber, StartDate, EndDate, StartNumber, EndNumber, UsedPower, Debit, Amount) VALUES ('2351511', '2899789082', 'سامان ', 'استحکام', 9143448721, '1397/06/02', '1397/08/02', 2300, 2310, 10, 0, 10000);" +
            "INSERT INTO  power (AboneID, ID, FirstName, LastName, CellPhoneNumber, StartDate, EndDate, StartNumber, EndNumber, UsedPower, Debit, Amount) VALUES ('2383828', '4172349420', 'فتح اله ', 'رادفر', 9141490359, '1397/06/02', '1397/08/02', 56000, 60000, 4000, 0, 1240000);" +
            "INSERT INTO  power (AboneID, ID, FirstName, LastName, CellPhoneNumber, StartDate, EndDate, StartNumber, EndNumber, UsedPower, Debit, Amount) VALUES ('2358049', '6469166261', 'شهرام', 'نصری', 9128687817, '1397/06/02', '1397/08/02', 60000, 61100, 1100, 0, 780000);" +
            "INSERT INTO  power (AboneID, ID, FirstName, LastName, CellPhoneNumber, StartDate, EndDate, StartNumber, EndNumber, UsedPower, Debit, Amount) VALUES ('2354993', '3820636374', 'جمال', 'کریمی', 9188762795, '1397/06/02', '1397/08/02', 12000, 15200, 3200, 320000, 156000);" +
            "INSERT INTO  power (AboneID, ID, FirstName, LastName, CellPhoneNumber, StartDate, EndDate, StartNumber, EndNumber, UsedPower, Debit, Amount) VALUES ('2354367', '2889951782', 'خضر', 'خضری', 9141672311, '1397/06/02', '1397/08/02', 32200, 33000, 800, 0, 230000);" +
            "INSERT INTO  power (AboneID, ID, FirstName, LastName, CellPhoneNumber, StartDate, EndDate, StartNumber, EndNumber, UsedPower, Debit, Amount) VALUES ('2363408', '5230043814', 'مجتبی ', 'کمالی', 9159250971, '1397/06/02', '1397/08/02', 63000, 63600, 600, 450000, 610000);" +
            "" +
            "CREATE TABLE  IF NOT EXISTS Panel(" +
            "Parvandeh varchar(255) NULL," +
            "ID varchar(255) NULL," +
            "FirstName varchar(255) NULL," +
            "LastName varchar(255) NULL," +
            "CellPhoneNumber float NULL," +
            "LastPayDate float NULL," +
            "StartYear float NULL," +
            "EndYear float NULL," +
            "Tax float NULL," +
            "Penalty float NULL," +
            "Amount float NULL" +
            ");" +
            "" +
            "INSERT INTO  Panel (Parvandeh, ID, FirstName, LastName, CellPhoneNumber, LastPayDate, StartYear, EndYear, Tax, Penalty, Amount) VALUES ('10774', '0872026337', 'جواد ', 'هشیار', 9151885012, 0, 0, 0, 0, 0, 0);" +
            "INSERT INTO  Panel (Parvandeh, ID, FirstName, LastName, CellPhoneNumber, LastPayDate, StartYear, EndYear, Tax, Penalty, Amount) VALUES ('11752', '3820757430', 'حسین', 'پروین', 9188788205, '1395/03/03', 1396, 1397, 1250000, 250000, 1500000);" +
            "INSERT INTO  Panel (Parvandeh, ID, FirstName, LastName, CellPhoneNumber, LastPayDate, StartYear, EndYear, Tax, Penalty, Amount) VALUES ('19989', '6409955669', 'حجت ', 'قربانی ', 9144628878, 0, 0, 0, 0, 0, 0);" +
            "INSERT INTO  Panel (Parvandeh, ID, FirstName, LastName, CellPhoneNumber, LastPayDate, StartYear, EndYear, Tax, Penalty, Amount) VALUES ('11703', '3810412996', 'حمید', 'نورافکن', 9388333686, '1395/03/03', 1396, 1397, 1250000, 250000, 1500000);" +
            "INSERT INTO  Panel (Parvandeh, ID, FirstName, LastName, CellPhoneNumber, LastPayDate, StartYear, EndYear, Tax, Penalty, Amount) VALUES ('15339', '2899789082', 'سامان ', 'استحکام', 9143448721, 0, 0, 0, 0, 0, 0);" +
            "INSERT INTO  Panel (Parvandeh, ID, FirstName, LastName, CellPhoneNumber, LastPayDate, StartYear, EndYear, Tax, Penalty, Amount) VALUES ('18672', '4172349420', 'فتح اله ', 'رادفر', 9141490359, 0, 0, 0, 0, 0, 0);" +
            "INSERT INTO  Panel (Parvandeh, ID, FirstName, LastName, CellPhoneNumber, LastPayDate, StartYear, EndYear, Tax, Penalty, Amount) VALUES ('17732', '6469166261', 'شهرام', 'نصری', 9128687817, 0, 0, 0, 0, 0, 0);" +
            "INSERT INTO  Panel (Parvandeh, ID, FirstName, LastName, CellPhoneNumber, LastPayDate, StartYear, EndYear, Tax, Penalty, Amount) VALUES ('17563', '3820636374', 'جمال', 'کریمی', 9188762795, '1395/03/03', 1396, 1397, 1250000, 250000, 1500000);" +
            "INSERT INTO  Panel (Parvandeh, ID, FirstName, LastName, CellPhoneNumber, LastPayDate, StartYear, EndYear, Tax, Penalty, Amount) VALUES ('18383', '2889951782', 'خضر', 'خضری', 9141672311, 0, 0, 0, 0, 0, 0);" +
            "INSERT INTO  Panel (Parvandeh, ID, FirstName, LastName, CellPhoneNumber, LastPayDate, StartYear, EndYear, Tax, Penalty, Amount) VALUES ('16924', '5230043814', 'مجتبی ', 'کمالی', 9159250971, 0, 0, 0, 0, 0, 0);" +
            "" +
            "" +
            "CREATE TABLE  IF NOT EXISTS Nosazi(" +
            "Parvandeh varchar(255) NULL," +
            "ID varchar(255) NULL," +
            "FirstName varchar(255) NULL," +
            "LastName varchar(255) NULL," +
            "CellPhoneNumber float NULL," +
            "LastPayDate varchar(255) NULL," +
            "StartYear float NULL," +
            "EndYear float NULL," +
            "Services float NULL," +
            "Debit float NULL," +
            "Amount float NULL" +
            ") ;" +
            "" +
            "INSERT INTO  Nosazi (Parvandeh, ID, FirstName, LastName, CellPhoneNumber, LastPayDate, StartYear, EndYear, Services, Debit, Amount) VALUES ('31893', '0872026337', 'جواد ', 'هشیار', 9151885012, '1393/02/26', 1394, 1397, 530000, 600000, 1130000);" +
            "INSERT INTO  Nosazi (Parvandeh, ID, FirstName, LastName, CellPhoneNumber, LastPayDate, StartYear, EndYear, Services, Debit, Amount) VALUES ('31245', '3820757430', 'حسین', 'پروین', 9188788205, '1392/02/26', 1393, 1397, 620000, 600000, 1220000);" +
            "INSERT INTO  Nosazi (Parvandeh, ID, FirstName, LastName, CellPhoneNumber, LastPayDate, StartYear, EndYear, Services, Debit, Amount) VALUES ('31711', '6409955669', 'حجت ', 'قربانی ', 9144628878, '1393/02/26', 1394, 1397, 530000, 0, 530000);" +
            "INSERT INTO  Nosazi (Parvandeh, ID, FirstName, LastName, CellPhoneNumber, LastPayDate, StartYear, EndYear, Services, Debit, Amount) VALUES ('31344', '3810412996', 'حمید', 'نورافکن', 9388333686, '1396/01/25', 1397, 1397, 120000, 0, 120000);" +
            "INSERT INTO  Nosazi (Parvandeh, ID, FirstName, LastName, CellPhoneNumber, LastPayDate, StartYear, EndYear, Services, Debit, Amount) VALUES ('31271', '2899789082', 'سامان ', 'استحکام', 9143448721, '1393/02/26', 1394, 1397, 530000, 600000, 1130000);" +
            "INSERT INTO  Nosazi (Parvandeh, ID, FirstName, LastName, CellPhoneNumber, LastPayDate, StartYear, EndYear, Services, Debit, Amount) VALUES ('31952', '4172349420', 'فتح اله ', 'رادفر', 9141490359, '1395/02/36', 1396, 1397, 260000, 120000, 380000);" +
            "INSERT INTO  Nosazi (Parvandeh, ID, FirstName, LastName, CellPhoneNumber, LastPayDate, StartYear, EndYear, Services, Debit, Amount) VALUES ('31754', '6469166261', 'شهرام', 'نصری', 9128687817, '1393/11/26', 1394, 1397, 530000, 0, 530000);" +
            "INSERT INTO  Nosazi (Parvandeh, ID, FirstName, LastName, CellPhoneNumber, LastPayDate, StartYear, EndYear, Services, Debit, Amount) VALUES ('31996', '3820636374', 'جمال', 'کریمی', 9188762795, '1393/06/31', 1394, 1397, 530000, 0, 530000);" +
            "INSERT INTO  Nosazi (Parvandeh, ID, FirstName, LastName, CellPhoneNumber, LastPayDate, StartYear, EndYear, Services, Debit, Amount) VALUES ('31157', '2889951782', 'خضر', 'خضری', 9141672311, '1370/01/23', 1371, 1397, 530000, 3200000, 3730000);" +
            "INSERT INTO  Nosazi (Parvandeh, ID, FirstName, LastName, CellPhoneNumber, LastPayDate, StartYear, EndYear, Services, Debit, Amount) VALUES ('31609', '5230043814', 'مجتبی ', 'کمالی', 9159250971, '1393/09/21', 1394, 1397, 530000, 0, 530000);" +
            "" +
            "CREATE TABLE  IF NOT EXISTS Gas(" +
            "AboneID varchar(255) NULL," +
            "ID varchar(255) NULL," +
            "FirstName varchar(255) NULL," +
            "LastName varchar(255) NULL," +
            "CellPhoneNumber float NULL," +
            "StartDate varchar(255) NULL," +
            "EndDate varchar(255) NULL," +
            "StartNumber float NULL," +
            "EndNumber float NULL," +
            "UsedGas float NULL," +
            "Units float NULL," +
            "Debit float NULL," +
            "Amount float NULL" +
            ") ;" +
            "" +
            "INSERT INTO  Gas (AboneID, ID, FirstName, LastName, CellPhoneNumber, StartDate, EndDate, StartNumber, EndNumber, UsedGas, Units, Debit, Amount) VALUES ('8219893', '0872026337', 'جواد ', 'هشیار', 9151885012, '1397/05/01', '1397/0/01', 1020, 1800, 780, 1, 150000, 800000);" +
            "INSERT INTO  Gas (AboneID, ID, FirstName, LastName, CellPhoneNumber, StartDate, EndDate, StartNumber, EndNumber, UsedGas, Units, Debit, Amount) VALUES ('8223941', '3820757430', 'حسین', 'پروین', 9188788205, '1397/05/01', '1397/0/01', 2000, 2168, 168, 1, 0, 260000);" +
            "INSERT INTO  Gas (AboneID, ID, FirstName, LastName, CellPhoneNumber, StartDate, EndDate, StartNumber, EndNumber, UsedGas, Units, Debit, Amount) VALUES ('8278200', '6409955669', 'حجت ', 'قربانی ', 9144628878, '1397/05/01', '1397/0/01', 5010, 5800, 790, 1, 0, 780000);" +
            "INSERT INTO  Gas (AboneID, ID, FirstName, LastName, CellPhoneNumber, StartDate, EndDate, StartNumber, EndNumber, UsedGas, Units, Debit, Amount) VALUES ('8247888', '3810412996', 'حمید', 'نورافکن', 9388333686, '1397/05/01', '1397/0/01', 200, 350, 150, 1, 0, 300000);" +
            "INSERT INTO  Gas (AboneID, ID, FirstName, LastName, CellPhoneNumber, StartDate, EndDate, StartNumber, EndNumber, UsedGas, Units, Debit, Amount) VALUES ('8239576', '2899789082', 'سامان ', 'استحکام', 9143448721, '1397/05/01', '1397/0/01', 5630, 5680, 50, 1, 0, 150000);" +
            "INSERT INTO  Gas (AboneID, ID, FirstName, LastName, CellPhoneNumber, StartDate, EndDate, StartNumber, EndNumber, UsedGas, Units, Debit, Amount) VALUES ('8212204', '4172349420', 'فتح اله ', 'رادفر', 9141490359, '1397/05/01', '1397/0/01', 3000, 4050, 1050, 2, 0, 230000);" +
            "INSERT INTO  Gas (AboneID, ID, FirstName, LastName, CellPhoneNumber, StartDate, EndDate, StartNumber, EndNumber, UsedGas, Units, Debit, Amount) VALUES ('8267528', '6469166261', 'شهرام', 'نصری', 9128687817, '1397/05/01', '1397/0/01', 5612, 800, 1020, 220, 0, 300000);" +
            "INSERT INTO  Gas (AboneID, ID, FirstName, LastName, CellPhoneNumber, StartDate, EndDate, StartNumber, EndNumber, UsedGas, Units, Debit, Amount) VALUES ('8236185', '3820636374', 'جمال', 'کریمی', 9188762795, '1397/05/01', '1397/0/01', 950, 1051, 101, 1, 0, 420000);" +
            "INSERT INTO  Gas (AboneID, ID, FirstName, LastName, CellPhoneNumber, StartDate, EndDate, StartNumber, EndNumber, UsedGas, Units, Debit, Amount) VALUES ('8264861', '2889951782', 'خضر', 'خضری', 9141672311, '1397/05/01', '1397/0/01', 412, 500, 88, 1, 0, 310000);" +
            "INSERT INTO  Gas (AboneID, ID, FirstName, LastName, CellPhoneNumber, StartDate, EndDate, StartNumber, EndNumber, UsedGas, Units, Debit, Amount) VALUES ('8265344', '5230043814', 'مجتبی ', 'کمالی', 9159250971, '1397/05/01', '1397/0/01', 8000, 12000, 4000, 2, 120000, 2450000);" +
            "" +
            "CREATE TABLE  IF NOT EXISTS CellPhone(" +
            "AboneID varchar(255) NULL," +
            "ID varchar(255) NULL," +
            "FirstName varchar(255) NULL," +
            "LastName varchar(255) NULL," +
            "CellPhoneNumber float NULL," +
            "StartDate varchar(255) NULL," +
            "EndDate varchar(255) NULL," +
            "UsedInMinutes float NULL," +
            "Debits float NULL," +
            "Amount float NULL" +
            ");" +
            "" +
            "INSERT INTO  CellPhone (AboneID, ID, FirstName, LastName, CellPhoneNumber, StartDate, EndDate, UsedInMinutes, Debits, Amount) VALUES ('4433665685', '0872026337', 'جواد ', 'هشیار', 9151885012, '1397/06/31', '1397/07/31', 560, 0, 360000);" +
            "INSERT INTO  CellPhone (AboneID, ID, FirstName, LastName, CellPhoneNumber, StartDate, EndDate, UsedInMinutes, Debits, Amount) VALUES ('2154123657', '3820757430', 'حسین', 'پروین', 9188788205, '1397/06/31', '1397/07/31', 1200, 0, 1210000);" +
            "INSERT INTO  CellPhone (AboneID, ID, FirstName, LastName, CellPhoneNumber, StartDate, EndDate, UsedInMinutes, Debits, Amount) VALUES ('4155621547', '6409955669', 'حجت ', 'قربانی ', 9144628878, '1397/06/31', '1397/07/31', 100, 190000, 380000);" +
            "INSERT INTO  CellPhone (AboneID, ID, FirstName, LastName, CellPhoneNumber, StartDate, EndDate, UsedInMinutes, Debits, Amount) VALUES ('2166335985', '3810412996', 'حمید', 'نورافکن', 9388333686, '1397/06/31', '1397/07/31', 151, 0, 390000);" +
            "INSERT INTO  CellPhone (AboneID, ID, FirstName, LastName, CellPhoneNumber, StartDate, EndDate, UsedInMinutes, Debits, Amount) VALUES ('4465984125', '2899789082', 'سامان ', 'استحکام', 9143448721, '1397/06/31', '1397/07/31', 200, 0, 560000);" +
            "INSERT INTO  CellPhone (AboneID, ID, FirstName, LastName, CellPhoneNumber, StartDate, EndDate, UsedInMinutes, Debits, Amount) VALUES ('7133659851', '4172349420', 'فتح اله ', 'رادفر', 9141490359, '1397/06/31', '1397/07/31', 78, 0, 180000);" +
            "INSERT INTO  CellPhone (AboneID, ID, FirstName, LastName, CellPhoneNumber, StartDate, EndDate, UsedInMinutes, Debits, Amount) VALUES ('8133265874', '6469166261', 'شهرام', 'نصری', 9128687817, '1397/06/31', '1397/07/31', 111, 0, 370000);" +
            "INSERT INTO  CellPhone (AboneID, ID, FirstName, LastName, CellPhoneNumber, StartDate, EndDate, UsedInMinutes, Debits, Amount) VALUES ('4433659855', '3820636374', 'جمال', 'کریمی', 9188762795, '1397/06/31', '1397/07/31', 1200, 0, 2890000);" +
            "INSERT INTO  CellPhone (AboneID, ID, FirstName, LastName, CellPhoneNumber, StartDate, EndDate, UsedInMinutes, Debits, Amount) VALUES ('6122365896', '2889951782', 'خضر', 'خضری', 9141672311, '1397/06/31', '1397/07/31', 56, 0, 180000);" +
            "INSERT INTO  CellPhone (AboneID, ID, FirstName, LastName, CellPhoneNumber, StartDate, EndDate, UsedInMinutes, Debits, Amount) VALUES ('8845986985', '5230043814', 'مجتبی ', 'کمالی', 9159250971, '1397/09/30', '1397/07/31', 14, 0, 90000);" +
            "" +
            "CREATE TABLE  IF NOT EXISTS Business(" +
            "Parvandeh varchar(255) NULL," +
            "ID varchar(255) NULL," +
            "FirstName varchar(255) NULL," +
            "LastName varchar(255) NULL," +
            "CellPhoneNumber float NULL," +
            "LastPayDate varchar(255) NULL," +
            "StartYear float NULL," +
            "EndYear float NULL," +
            "Tax float NULL," +
            "Penalty float NULL," +
            "Amount float NULL" +
            ");" +
            "INSERT INTO  Business (Parvandeh, ID, FirstName, LastName, CellPhoneNumber, LastPayDate, StartYear, EndYear, Tax, Penalty, Amount) VALUES ('23534', '0872026337', 'جواد ', 'هشیار', 9151885012, '1395/06/31', 1396, 1397, 120000, 12000, 132000);" +
            "INSERT INTO  Business (Parvandeh, ID, FirstName, LastName, CellPhoneNumber, LastPayDate, StartYear, EndYear, Tax, Penalty, Amount) VALUES ('23984', '3820757430', 'حسین', 'پروین', 9188788205, 0, 0, 0, 0, 0, 0);" +
            "INSERT INTO  Business (Parvandeh, ID, FirstName, LastName, CellPhoneNumber, LastPayDate, StartYear, EndYear, Tax, Penalty, Amount) VALUES ('23351', '6409955669', 'حجت ', 'قربانی ', 9144628878, 0, 0, 0, 0, 0, 0);" +
            "INSERT INTO  Business (Parvandeh, ID, FirstName, LastName, CellPhoneNumber, LastPayDate, StartYear, EndYear, Tax, Penalty, Amount) VALUES ('23194', '3810412996', 'حمید', 'نورافکن', 9388333686, '1395/06/31', 1396, 1397, 120000, 12000, 132000);" +
            "INSERT INTO  Business (Parvandeh, ID, FirstName, LastName, CellPhoneNumber, LastPayDate, StartYear, EndYear, Tax, Penalty, Amount) VALUES ('23529', '2899789082', 'سامان ', 'استحکام', 9143448721, 0, 0, 0, 0, 0, 0);" +
            "INSERT INTO  Business (Parvandeh, ID, FirstName, LastName, CellPhoneNumber, LastPayDate, StartYear, EndYear, Tax, Penalty, Amount) VALUES ('23251', '4172349420', 'فتح اله ', 'رادفر', 9141490359, 0, 0, 0, 0, 0, 0);" +
            "INSERT INTO  Business (Parvandeh, ID, FirstName, LastName, CellPhoneNumber, LastPayDate, StartYear, EndYear, Tax, Penalty, Amount) VALUES ('23845', '6469166261', 'شهرام', 'نصری', 9128687817, 0, 0, 0, 0, 0, 0);" +
            "INSERT INTO  Business (Parvandeh, ID, FirstName, LastName, CellPhoneNumber, LastPayDate, StartYear, EndYear, Tax, Penalty, Amount) VALUES ('23841', '3820636374', 'جمال', 'کریمی', 9188762795, 0, 0, 0, 0, 0, 0);" +
            "INSERT INTO  Business (Parvandeh, ID, FirstName, LastName, CellPhoneNumber, LastPayDate, StartYear, EndYear, Tax, Penalty, Amount) VALUES ('23379', '2889951782', 'خضر', 'خضری', 9141672311, 0, 0, 0, 0, 0, 0);" +
            "INSERT INTO  Business (Parvandeh, ID, FirstName, LastName, CellPhoneNumber, LastPayDate, StartYear, EndYear, Tax, Penalty, Amount) VALUES ('23342', '5230043814', 'مجتبی ', 'کمالی', 9159250971, '1395/06/31', 1396, 1397, 120000, 12000, 132000);";

}
