package com.jhanakdidwania.officespace;

import android.print.PageRange;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XmlParser {
    private List<Features> features = new ArrayList<Features>();
    private Features feature = null; //object of type Features
    private static String LicenseName;
    private static String LicenseVersion;
    private String groupName;

    public String getLicenseName() {
        return LicenseName;
    }

    public void setLicenseName(String licenseName) {
        LicenseName = licenseName;
    }

    public String getLicenseVersion() {
        return LicenseVersion;
    }

    public void setLicenseVersion(String licenseVersion) {
        LicenseVersion = licenseVersion;
    }

    //This function will return the list of features where each list node represent the object of type Features
    private List<Features> getFeatures() {
        return features;
    }

    /* parser.next() sets the eventType to the next tag in the xml file and moves the cursor pointer to next event
    START_TAG start tag was read
	TEXT text was read, text can be retrieved using getText() method
	END_TAG end tag was read
	END_DOCUMENT no more events are available
    */

    public List<Features> parse(InputStream is) throws XmlPullParserException, IOException {
        XmlPullParserFactory xmlPullParserFactory = null;

        xmlPullParserFactory = XmlPullParserFactory.newInstance();
        //xmlPullParserFactory.setNamespaceAware(true);
        XmlPullParser parser = null;
        parser = xmlPullParserFactory.newPullParser();

        parser.setInput(is, null);

        int event = parser.getEventType();
        event = parser.nextTag(); //we moved to LCNS tag
        String name = parser.getName();
        setLicenseName(parser.getAttributeValue(null, "NAME"));
        setLicenseVersion(parser.getAttributeValue(null, "VER"));

        event = parser.nextTag();
        name = parser.getName();

        while(event != XmlPullParser.END_DOCUMENT){
            String grpName = parser.getAttributeValue(null, "NAME");
            Log.d("OfficeSpace",grpName);

            while(event == XmlPullParser.START_TAG && parser.getName().equals("GRP")){
                grpName = parser.getAttributeValue(null, "NAME");
                Log.d("OfficeSpace",grpName);
                Log.d("OfficeSpace",parser.getName());
                event = parser.nextTag();
                Log.d("OfficeSpace",parser.getName());

                while(event == XmlPullParser.START_TAG && parser.getName().equalsIgnoreCase("value")){
                    name = parser.getName();
                    Log.d("OfficeSpace", name);
                    //event = parser.next();
                    feature = new Features();
                    feature.setTitle(grpName);
                    feature.setUID(parser.getAttributeValue(null, "UID"));
                    feature.setVal(Integer.parseInt(parser.getAttributeValue(null,"VAL")));
                    feature.setName(parser.getAttributeValue(null,"N"));
                    feature.setDescription(parser.getAttributeValue(null,"D"));
                    feature.setVER_BASIC(Integer.parseInt(parser.getAttributeValue(null,"VER_BASIC")));
                    feature.setVER_GOLD(Integer.parseInt(parser.getAttributeValue(null,"VER_GOLD")));
                    feature.setVER_PLATINUM(Integer.parseInt(parser.getAttributeValue(null,"VER_PLATINUM")));
                    feature.setVER_SILVER(Integer.parseInt(parser.getAttributeValue(null,"VER_SILVER")));
                    if(grpName.equalsIgnoreCase("DAYS") || grpName.equalsIgnoreCase("Range"))
                    {
                        feature.setRANGE_MAX(Integer.parseInt(parser.getAttributeValue(null,"RANGE_MAX")));
                        feature.setRANGE_MIN(Integer.parseInt(parser.getAttributeValue(null,"RANGE_MIN")));
                    }

                    event = parser.nextTag();
                    name = parser.getName();
                    Log.d("OfficeSpace", "Event is: "+event);
                    features.add(feature);
                    Log.d("OfficeSpace", "Size is: "+features.size());
                    name = parser.getName();
                    //Log.d("OfficeSpace","Event is"+ parser.nextTag());
                    Log.d("OfficeSpace", "Checking");
                    event = parser.nextTag();
                    Log.d("OfficeSpace","Event is"+ event);
                    if(event == XmlPullParser.END_TAG){
                        event = parser.nextTag();
                        Log.d("OfficeSpace","Event is: "+event);
                        name = parser.getName();
                        Log.d("OfficeSpace",name);
                    }
                }
            }
            if(parser.getName().equals("LCNS")) break;
        }
        Log.d("OfficeSpace", "Size is:" +features.size());
        return features;
    }
}