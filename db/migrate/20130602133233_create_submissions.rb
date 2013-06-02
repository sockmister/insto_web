class CreateSubmissions < ActiveRecord::Migration
  def change
    create_table :submissions do |t|
    	t.references :location_id
    	t.references :user_id

      t.timestamps
    end
  end
end
