//package com.ph.grib2tools.test;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;


import com.google.common.collect.Sets;
import ucar.coord.Coordinate;
import ucar.coord.CoordinateRuntime;
import ucar.ma2.Array;
import ucar.ma2.InvalidRangeException;
import ucar.nc2.Attribute;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;
import ucar.nc2.dataset.CoordinateAxis;
import ucar.nc2.dataset.CoordinateSystem;
import ucar.nc2.dataset.NetcdfDataset;
import ucar.nc2.dt.GridCoordSystem;
import ucar.nc2.dt.grid.GridCoordSys;
import ucar.nc2.grib.collection.Grib2Iosp;
import ucar.nc2.iosp.IOServiceProvider;
import ucar.nc2.time.CalendarDate;
import ucar.unidata.io.RandomAccessFile;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;

public class GRIB2FileTest {
    
        public static float convKeltoC(double a)
        {
            float k = (float)(a - 273.15);
            //System.out.printf("%.2f",k);
            return k;
        }

	public static void main(String[] args) throws IOException {

        String pathfile = "./format.grib";

//Create RandomAccessFile
        RandomAccessFile gribfile = null;

        try {
            gribfile = new ucar.unidata.io.RandomAccessFile(pathfile, "rw");
            final NetcdfFile netcdfFile = NetcdfFile.open(pathfile);
            System.out.println(netcdfFile.getDetailInfo());
            NetcdfDataset netcdfDataset = NetcdfDataset.wrap(netcdfFile, Sets.newHashSet(NetcdfDataset.Enhance.CoordSystems));
            List<CoordinateSystem> coordinateSystems = netcdfDataset.getCoordinateSystems();

            final ucar.nc2.grib.collection.Grib2Iosp iosp = (Grib2Iosp) netcdfFile.getIosp();
            final Object gribCustomizer = iosp.getGribCustomizer();


            final List<Variable> variables = netcdfFile.getVariables();
            for (Variable v:
                 variables) {
                System.out.println(v.getNameAndDimensions());
            }
            boolean heightAxisFound = false;
            System.out.println("Coordinate system");
            for (CoordinateSystem coordinateSystem : coordinateSystems) {
                final String desc = coordinateSystem.toString();
                
                System.out.println(desc);

            }
            System.out.println();
            //final Variable temp = netcdfFile.findVariable("Temperature_height_above_ground");
            //final Variable prec = netcdfFile.findVariable("Total_precipitation_surface_6_Hour_Accumulation");
            final Variable temp = netcdfFile.findVariable("Temperature_height_above_ground_Mixed_intervals_Average");
            final Variable prec = netcdfFile.findVariable("Total_precipitation_rate_surface_Mixed_intervals_Accumulation");
            for (CoordinateSystem coordinateSystem : coordinateSystems) {
                if(coordinateSystem.isCoordinateSystemFor(prec) && coordinateSystem.isGeoReferencing()){
                    System.out.println("Precipitazione");
                    System.out.println();
                }else if(coordinateSystem.isCoordinateSystemFor(temp) && coordinateSystem.isGeoReferencing()){
                    System.out.println("Temperatura");



                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
