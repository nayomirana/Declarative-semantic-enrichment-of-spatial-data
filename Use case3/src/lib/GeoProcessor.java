import java.text.SimpleDateFormat;
import java.util.Date;
import org.geotools.geometry.jts.JTS;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
/*import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.CRS;
import org.opengis.referencing.crs.CoordinateReferenceSystem;*/
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;
import java.util.ArrayList;
import java.util.Date;
/*import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;*/
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.io.WKTWriter;
import org.locationtech.jts.geom.Envelope;
import org.geotools.util.Utilities;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Polygon;

public class GeoProcessor {
    public static String parsePointLatF(String s) {
        return s.replace("POINT ", "").replace('(', ' ').replace(')', ' ').trim().split("\s+")[0];
    }

    public static String formatDate(String myDate) {

        if (myDate == null || myDate.trim().isEmpty()) {
            return "0001-01-01";
        }

        SimpleDateFormat dateOnlyFormat = new SimpleDateFormat("yyyy-MM-dd"); // 2020-05-03

        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); // 2023-10-17T15:30:45

        SimpleDateFormat compactDateTimeFormat = new SimpleDateFormat("yyyyMMddHHmmss"); // 20230302142751

        SimpleDateFormat compactDateOnlyFormat = new SimpleDateFormat("yyyyMMdd"); // 20170403

        // Formats using "/"
        SimpleDateFormat slashYMDFormat = new SimpleDateFormat("yyyy/MM/dd"); // 2020/05/03

        SimpleDateFormat slashDMYFormat = new SimpleDateFormat("dd/MM/yyyy"); // 03/05/2020

        SimpleDateFormat slashMDYFormat = new SimpleDateFormat("MM/dd/yyyy"); // 05/03/2020

        SimpleDateFormat slashYMDTimeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); // 2023/10/17 15:30:45

        SimpleDateFormat slashDMYTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); // 17/10/2023 15:30:45

        SimpleDateFormat slashMDYTimeFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss"); // 10/17/2023 15:30:45

        // Output format
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        SimpleDateFormat[] formats = {
                compactDateTimeFormat,
                compactDateOnlyFormat,
                dateTimeFormat,
                dateOnlyFormat,
                slashYMDTimeFormat,
                slashDMYTimeFormat,
                slashMDYTimeFormat,
                slashYMDFormat,
                slashDMYFormat,
                slashMDYFormat
        };

        for (SimpleDateFormat format : formats) {
            format.setLenient(false);
        }

        for (SimpleDateFormat format : formats) {
            try {
                Date date = format.parse(myDate);
                return outputFormat.format(date);
            } catch (Exception e) {
                // Try next format
            }
        }

        // If format is not recognized, return original value
        return myDate;
    }

    public static String getBoundingBoxOfPolygon(String wkt) {
        try {
            WKTReader reader = new WKTReader();
            Geometry geometry = reader.read(wkt);
            Envelope boundingBox = geometry.getEnvelopeInternal();

            return String.format("Min X: %.6f, Min Y: %.6f, Max X: %.6f, Max Y: %.6f",
                    boundingBox.getMinX(),
                    boundingBox.getMinY(),
                    boundingBox.getMaxX(),
                    boundingBox.getMaxY());
        } catch (Exception e) {
            e.printStackTrace();
            return "Invalid WKT geometry";
        }
    }

    // Buffer distance : 10
    public static String getBufferedGeometry(String geometry) {
        try {
            WKTReader reader = new WKTReader();
            Geometry geometryR = reader.read(geometry);
            Double distance = (double) 10;
            Geometry bufferedGeometry = geometryR.buffer(distance);

            WKTWriter writer = new WKTWriter();
            return writer.write(bufferedGeometry);
        } catch (Exception e) {
            e.printStackTrace();
            return "Invalid WKT geometry";
        }
    }

    public static String getBufferedGeomForGivenDist(String input1, String input2) {
        try {
            WKTReader reader = new WKTReader();
            Geometry geometryR = reader.read(input1);
            System.setProperty("org.geotools.referencing.forceXY", "true");

            Double distanceDouble = Double.parseDouble(input2);
            Geometry bufferedGeometry = geometryR.buffer(distanceDouble);

            WKTWriter writer = new WKTWriter();
            return writer.write(bufferedGeometry);
        } catch (Exception e) {
            e.printStackTrace();
            return "Invalid WKT geometry";
        }
    }

    public static String setGeometryToCRS_4326(String wkt) throws MismatchedDimensionException, TransformException,
            ParseException, NoSuchAuthorityCodeException, FactoryException {

        String result = "";

        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(null);

        CoordinateReferenceSystem coordinateReferenceSystem = DefaultGeographicCRS.WGS84;

        WKTReader reader = new WKTReader(geometryFactory);
        Geometry geometry = (Geometry) reader.read(wkt);

        // CoordinateReferenceSystem targetCRS = CRS.decode("EPSG:4283");
        CoordinateReferenceSystem targetCRS = CRS.decode("EPSG:4326");
        MathTransform transform = CRS.findMathTransform(coordinateReferenceSystem, targetCRS, true);
        Geometry target = JTS.transform(geometry, transform);

        result = target.toString();

        return result;

    }

    public static String setGeometryToCRSForGivenCode(String wkt, String code) throws MismatchedDimensionException,
            TransformException, ParseException, NoSuchAuthorityCodeException, FactoryException {
        String result = "";
        System.setProperty("org.geotools.referencing.forceXY", "true");
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(null);
        // https://epsg.io/4326
        CoordinateReferenceSystem coordinateReferenceSystem = DefaultGeographicCRS.WGS84;
        WKTReader reader = new WKTReader(geometryFactory);
        Geometry geometry = (Geometry) reader.read(wkt);
        // https://docs.geotools.org/stable/javadocs/org/geotools/referencing/CRS.html
        CoordinateReferenceSystem targetCRS = CRS.decode(code.toString().trim());
        MathTransform transform = CRS.findMathTransform(coordinateReferenceSystem, targetCRS);
        Geometry target = JTS.transform(geometry, transform);

        result = target.toText();

        return result;

    }

    public static String setGeomToCRSForGivenSourceTargetCodes(String wkt, String source, String target)
            throws MismatchedDimensionException, TransformException, ParseException, NoSuchAuthorityCodeException,
            FactoryException {
        String result = "";

        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(null);

        CoordinateReferenceSystem coordinateReferenceSystem = CRS.decode(source.toString().trim(), true);
        WKTReader reader = new WKTReader(geometryFactory);
        Geometry geometry = (Geometry) reader.read(wkt);

        CoordinateReferenceSystem targetCRS = CRS.decode(target.toString().trim(), true);
        MathTransform transform = CRS.findMathTransform(coordinateReferenceSystem, targetCRS); // Added lenient:false
                                                                                               // 2025/04/07
        Geometry targetGeometry = JTS.transform(geometry, transform);

        result = targetGeometry.toString();

        return result;

    }

}