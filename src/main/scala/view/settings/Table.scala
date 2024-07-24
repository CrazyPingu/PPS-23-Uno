package view.settings

import java.awt.Color
import javax.swing.{JTable, SwingConstants}
import javax.swing.border.EmptyBorder
import javax.swing.table.{DefaultTableCellRenderer, DefaultTableModel, TableColumn}

class Table(data: Array[Array[AnyRef]], columnNames: Array[AnyRef], columnSize: Array[Int]) extends JTable(new DefaultTableModel(data, columnNames)):

  for i <- columnSize.indices do
    val column: TableColumn = this.getColumnModel.getColumn(i)
    column.setPreferredWidth(columnSize(i))

  setFocusable(false)
  setEnabled(false)
  setFont(getFont.deriveFont(20f))
  applyPadding()

  private def applyPadding(): Unit =
    val renderer = new DefaultTableCellRenderer:

      override def getTableCellRendererComponent(table: JTable, value: Any, isSelected: Boolean, hasFocus: Boolean, row: Int, column: Int): java.awt.Component =
        val component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column)
        value match
          case "Achieved" =>
            component.setForeground(Color.GREEN)
          case "Not achieved" =>
            component.setForeground(Color.RED)
          case _ =>
            component.setBackground(Color.WHITE)
            component.setForeground(Color.BLACK)
        component

      setHorizontalAlignment(SwingConstants.CENTER)
      setBorder(new EmptyBorder(5, 5, 5, 5))

    for (i <- 0 until getColumnCount)do
      getColumnModel.getColumn(i).setCellRenderer(renderer)

