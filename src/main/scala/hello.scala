import java.io.File
import net.sf.samtools.SAMFileReader
import net.sf.samtools.SAMFileWriter
import net.sf.samtools.SAMFileWriterFactory
import net.sf.samtools.SAMRecord
import net.sf.samtools.SAMRecordIterator
import net.sf.samtools.SAMFileReader.ValidationStringency


object BamReader {
  def main(args: Array[String]): Unit = {

    val inFile = "/Users/yukke/dev/data/testREDItools/rna.bam"
    val inputSam: SAMFileReader = new SAMFileReader(new File(inFile))

    inputSam.setValidationStringency(ValidationStringency.SILENT)
    val iter: SAMRecordIterator = inputSam.iterator()

    while (iter.hasNext()) {
      val rec: SAMRecord = iter.next()
      if (!rec.getReadUnmappedFlag()) {
        var refName = rec.getReferenceName()
        var alignStart = rec.getAlignmentStart()
        var alignEnd = rec.getAlignmentEnd()
        var mapQuality = rec.getMappingQuality()
        var cigarString = rec.getCigarString()
        var readGroup = rec.getReadGroup()
        var header = rec.getHeader()
        println(refName, alignEnd - alignStart, rec.getReadString().take(10))
        //println(rec.getReferencePositionAtReadPosition(1))
        //println(f"$refName%s, $alignStart%d, $alignEnd%d")
      }
    }
    iter.close()
    inputSam.close()
  }
}
