package com.force5solutions.care.cc

import org.springframework.web.multipart.commons.CommonsMultipartFile
import org.codehaus.groovy.grails.commons.GrailsApplication
import grails.util.GrailsUtil
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.codehaus.groovy.grails.commons.ApplicationHolder
import javax.servlet.ServletContext

public class AppUtil {

    public static String getEmailFromSlid(String slid) {
        String email = ((GrailsUtil.environment == GrailsApplication.ENV_PRODUCTION) ? (slid + ConfigurationHolder.config.emailDomain) : ("care.force5+" + slid + "@gmail.com"))
        return email
    }

    public static Date getDateMinusPeriodUnit(Date date, Integer period, PeriodUnit periodUnit) {
        Date resultDate
        use(org.codehaus.groovy.runtime.TimeCategory) {
            switch (periodUnit) {
                case PeriodUnit.YEARS:
                    resultDate = date - period.years
                    break;
                case PeriodUnit.MONTHS:
                    resultDate = date - period.months
                    break;
                case PeriodUnit.WEEKS:
                    resultDate = date - period.weeks
                    break;
                case PeriodUnit.DAYS:
                    resultDate = date - period.days
                    break;
                case PeriodUnit.HOURS:
                    resultDate = date - period.hours
                    break;
                case PeriodUnit.MINUTES:
                    resultDate = date - period.minutes
                    break;
            }

        }
        return resultDate
    }

    public static Date getDatePlusPeriodUnit(Date date, Integer period, PeriodUnit periodUnit) {
        Date resultDate
        use(org.codehaus.groovy.runtime.TimeCategory) {
            switch (periodUnit) {
                case PeriodUnit.YEARS:
                    resultDate = date + period.years
                    break;
                case PeriodUnit.MONTHS:
                    resultDate = date + period.months
                    break;
                case PeriodUnit.WEEKS:
                    resultDate = date + period.weeks
                    break;
                case PeriodUnit.DAYS:
                    resultDate = date + period.days
                    break;
                case PeriodUnit.HOURS:
                    resultDate = date + period.hours
                    break;
                case PeriodUnit.MINUTES:
                    resultDate = date + period.minutes
                    break;
            }

        }
        return resultDate
    }

    public static Map getStateMap() {
        return [
                AL: "Alabama",
                AK: "Alaska",
                AZ: "Arizona",
                AR: "Arkansas",
                CA: "California",
                CO: "Colorado",
                CT: "Connecticut",
                DC: "District of Columbia",
                DE: "Delaware",
                FL: "Florida",
                GA: "Georgia",
                HI: "Hawaii",
                ID: "Idaho",
                IL: "Illinois",
                IN: "Indiana",
                IA: "Iowa",
                KS: "Kansas",
                KY: "Kentucky",
                LA: "Louisiana",
                ME: "Maine",
                MD: "Maryland",
                MA: "Massachusetts",
                MI: "Michigan",
                MN: "Minnesota",
                MS: "Mississippi",
                MO: "Missouri",
                MT: "Montana",
                NE: "Nebraska",
                NV: "Nevada",
                NH: "New Hampshire",
                NJ: "New Jersey",
                NM: "New Mexico",
                NY: "New York",
                NC: "North Carolina",
                ND: "North Dakota",
                OH: "Ohio",
                OK: "Oklahoma",
                OR: "Oregon",
                PA: "Pennsylvania",
                RI: "Rhode Island",
                SC: "South Carolina",
                SD: "South Dakota",
                TN: "Tennessee",
                TX: "Texas",
                UT: "Utah",
                VT: "Vermont",
                VA: "Virginia",
                WA: "Washington",
                WI: "Wisconsin",
                WV: "West Virginia",
                WY: "Wyoming"
        ]
    }

    public static String getStateCode(String state) {
        Map stateMap = getStateMap()
        if (stateMap.keySet().contains(state)) {
            return state
        }
        return stateMap.find {it.value == state}?.key
    }

    public static List<UploadedFile> populateAttachments(Map params) {
        List<UploadedFile> uploadedFiles = []
        Map files = params.findAll {key, value ->
            key.startsWith("multiFiles") &&
                    value.class.name == "org.springframework.web.multipart.commons.CommonsMultipartFile"
        }
        files.each {
            CommonsMultipartFile file = params."$it.key"
            if (!file.isEmpty()) {
                UploadedFile uploadedfile = new UploadedFile(fileName: file.getOriginalFilename(), bytes: file.getBytes());
                uploadedFiles << uploadedfile;
            }
        }
        return uploadedFiles
    }

    public static String getMimeContentType(String extension) {
        Map mimetypes = ['ai': 'application/postscript',
                'aif': 'audio/x-aiff',
                'aifc': 'audio/x-aiff',
                'aiff': 'audio/x-aiff',
                'asc': 'text/plain',
                'atom': 'application/atom+xml',
                'au': 'audio/basic',
                'avi': 'video/x-msvideo',
                'bcpio': 'application/x-bcpio',
                'bin': 'application/octet-stream',
                'bmp': 'image/bmp',
                'cdf': 'application/x-netcdf',
                'cgm': 'image/cgm',
                'class': 'application/octet-stream',
                'cpio': 'application/x-cpio',
                'cpt': 'application/mac-compactpro',
                'csh': 'application/x-csh',
                'css': 'text/css',
                'csv': 'text/plain',
                'dcr': 'application/x-director',
                'dif': 'video/x-dv',
                'dir': 'application/x-director',
                'djv': 'image/vnd.djvu',
                'djvu': 'image/vnd.djvu',
                'dll': 'application/octet-stream',
                'dmg': 'application/octet-stream',
                'dms': 'application/octet-stream',
                'doc': 'application/msword',
                'dtd': 'application/xml-dtd',
                'dv': 'video/x-dv',
                'dvi': 'application/x-dvi',
                'dxr': 'application/x-director',
                'eps': 'application/postscript',
                'etx': 'text/x-setext',
                'exe': 'application/octet-stream',
                'ez': 'application/andrew-inset',
                'gif': 'image/gif',
                'gram': 'application/srgs',
                'grxml': 'application/srgs+xml',
                'gtar': 'application/x-gtar',
                'hdf': 'application/x-hdf',
                'hqx': 'application/mac-binhex40',
                'htm': 'text/html',
                'html': 'text/html',
                'ice': 'x-conference/x-cooltalk',
                'ico': 'image/x-icon',
                'ics': 'text/calendar',
                'ief': 'image/ief',
                'ifb': 'text/calendar',
                'iges': 'model/iges',
                'igs': 'model/iges',
                'jnlp': 'application/x-java-jnlp-file',
                'jp2': 'image/jp2',
                'jpe': 'image/jpeg',
                'jpeg': 'image/jpeg',
                'jpg': 'image/jpeg',
                'js': 'application/x-javascript',
                'kar': 'audio/midi',
                'latex': 'application/x-latex',
                'lha': 'application/octet-stream',
                'lzh': 'application/octet-stream',
                'm3u': 'audio/x-mpegurl',
                'm4a': 'audio/mp4a-latm',
                'm4b': 'audio/mp4a-latm',
                'm4p': 'audio/mp4a-latm',
                'm4u': 'video/vnd.mpegurl',
                'm4v': 'video/x-m4v',
                'mac': 'image/x-macpaint',
                'man': 'application/x-troff-man',
                'mathml': 'application/mathml+xml',
                'me': 'application/x-troff-me',
                'mesh': 'model/mesh',
                'mid': 'audio/midi',
                'midi': 'audio/midi',
                'mif': 'application/vnd.mif',
                'mov': 'video/quicktime',
                'movie': 'video/x-sgi-movie',
                'mp2': 'audio/mpeg',
                'mp3': 'audio/mpeg',
                'mp4': 'video/mp4',
                'mpe': 'video/mpeg',
                'mpeg': 'video/mpeg',
                'mpg': 'video/mpeg',
                'mpga': 'audio/mpeg',
                'ms': 'application/x-troff-ms',
                'msh': 'model/mesh',
                'mxu': 'video/vnd.mpegurl',
                'nc': 'application/x-netcdf',
                'oda': 'application/oda',
                'ogg': 'application/ogg',
                'pbm': 'image/x-portable-bitmap',
                'pct': 'image/pict',
                'pdb': 'chemical/x-pdb',
                'pdf': 'application/pdf',
                'pgm': 'image/x-portable-graymap',
                'pgn': 'application/x-chess-pgn',
                'pic': 'image/pict',
                'pict': 'image/pict',
                'png': 'image/png',
                'pnm': 'image/x-portable-anymap',
                'pnt': 'image/x-macpaint',
                'pntg': 'image/x-macpaint',
                'ppm': 'image/x-portable-pixmap',
                'ppt': 'application/vnd.ms-powerpoint',
                'ps': 'application/postscript',
                'qt': 'video/quicktime',
                'qti': 'image/x-quicktime',
                'qtif': 'image/x-quicktime',
                'ra': 'audio/x-pn-realaudio',
                'ram': 'audio/x-pn-realaudio',
                'ras': 'image/x-cmu-raster',
                'rdf': 'application/rdf+xml',
                'rgb': 'image/x-rgb',
                'rm': 'application/vnd.rn-realmedia',
                'roff': 'application/x-troff',
                'rtf': 'text/rtf',
                'rtx': 'text/richtext',
                'sgm': 'text/sgml',
                'sgml': 'text/sgml',
                'sh': 'application/x-sh',
                'shar': 'application/x-shar',
                'silo': 'model/mesh',
                'sit': 'application/x-stuffit',
                'skd': 'application/x-koan',
                'skm': 'application/x-koan',
                'skp': 'application/x-koan',
                'skt': 'application/x-koan',
                'smi': 'application/smil',
                'smil': 'application/smil',
                'snd': 'audio/basic',
                'so': 'application/octet-stream',
                'spl': 'application/x-futuresplash',
                'src': 'application/x-wais-source',
                'sv4cpio': 'application/x-sv4cpio',
                'sv4crc': '	application/x-sv4crc',
                'svg': 'image/svg+xml',
                'swf': 'application/x-shockwave-flash',
                't': 'application/x-troff',
                'tar': 'application/x-tar',
                'tcl': 'application/x-tcl',
                'tex': 'application/x-tex',
                'texi': 'application/x-texinfo',
                'texinfo': '	application/x-texinfo',
                'tif': 'image/tiff',
                'tiff': 'image/tiff',
                'tr': 'application/x-troff',
                'tsv': 'text/tab-separated-values',
                'txt': 'text/plain',
                'ustar': 'application/x-ustar',
                'vcd': 'application/x-cdlink',
                'vrml': 'model/vrml',
                'vxml': 'application/voicexml+xml',
                'wav': 'audio/x-wav',
                'wbmp': 'image/vnd.wap.wbmp',
                'wbmxl': 'application/vnd.wap.wbxml',
                'wml': 'text/vnd.wap.wml',
                'wmlc': 'application/vnd.wap.wmlc',
                'wmls': 'text/vnd.wap.wmlscript',
                'wmlsc': '	application/vnd.wap.wmlscriptc',
                'wrl': 'model/vrml',
                'xbm': 'image/x-xbitmap',
                'xht': 'application/xhtml+xml',
                'xhtml': 'application/xhtml+xml',
                'xls': 'application/vnd.ms-excel',
                'xlsx': 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
                'xml': 'application/xml',
                'xpm': 'image/x-xpixmap',
                'xsl': 'application/xml',
                'xslt': 'application/xslt+xml',
                'xul': 'application/vnd.mozilla.xul+xml',
                'xwd': 'image/x-xwindowdump',
                'xyz': 'chemical/x-xyz',
                'zip': 'application/zip']
        return mimetypes.get(extension)
    }

    static String getFixturesDirectoryPath() {
        ServletContext servletContext = ApplicationHolder.application.parentContext.servletContext
        String pathSeparator = System.getProperty("file.separator")
        String webXmlFilePath = pathSeparator + "WEB-INF" + pathSeparator + "web.xml"
        String path = servletContext.getRealPath(webXmlFilePath)
        String basePath = path.substring(0, path.indexOf(webXmlFilePath))
        String fixturesPath = basePath + pathSeparator + "fixtures"
        return fixturesPath
    }
}